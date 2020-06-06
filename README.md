# liferay-test

## Testing portlets with services

Right now it can't be easily done, as both portlet and portal context are configured with similarly
named files (base-spring.xml, ...) and portlet xmls take priority.

TODO: can easyconf be instructed to return paths to files in portal-impl jar?

Best bet is to:
* copy portal Spring context configuration files (`portal-impl/META-INF/*-spring.xml`) to test path META-INF (`test/unit/META-INF/portal/*-spring.xml`),
* copy `liferay-test/src/main/resources/portal-ext.properties` to test path classpath (`test/unit/portal-ext.properties`),
* copy `spring.configs` property from `portal-impl/portal.properties` to `test/unit/portal-ext.properties` and replace `META-INF/` with `META-INF/portal/`,
* remove the persistence implementation you don't use (most likely `jpa-spring.xml`),
* comments in this property might cause an exception,
* including `ext-spring.xml` might cause an exception.

In your `@BeforeClass` method do `ctx.initializePortletServices("guestbook-portlet");`. This creates a spring context
for the portlet and caches the `PortalBeanLocator` instance.

## Usage

Use `PortalInitializer.initialize()` to start portal with all services:

```java
public class NewPortletTest
{
	private NewPortlet newPortlet; // MVCPortlet
	private static ControllerContext ctx;

	@BeforeClass
	public static void beforeClass() throws ReflectiveOperationException, SystemException
	{
		PortalInitializer.initialize();
		ctx = new ControllerContext();
		ctx.initializePortletServices("guestbook-portlet");
	}

	@Before
	public void before()
	{
		newPortlet = new NewPortlet();
		PortletContext pctx = new TestPortletContext("test");
		PortletConfig pcfg = new TestPortletConfig(pctx);
		newPortlet.init(pcfg);
	}

    /**
     * Render methods are not fully supported yet - PortletRequestDispatcher implementation missing.
     */
	@Test
	public void renderTest() throws PortletException, IOException, ReflectiveOperationException
	{

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();
		RenderRequest req = PortletRequestBuilder.newRenderRequest()
				.withWindowState(WindowState.NORMAL)
				.withPortletMode(PortletMode.VIEW)
				.withPortlet(ctx.getPortlet())
				.withThemeDisplay(themeDisplay)
				.build();
		RenderResponse resp = PortletResponseBuilder.newRenderResponse()
				.withPortletRequest(req)
				.build();

		newPortlet.render(req, resp);
	}

	@Test
	public void actionTest() throws ReflectiveOperationException, PortalException, SystemException, IOException, NamingException, SQLException
	{
		ActionRequest req = PortletRequestBuilder.newActionRequest()
				.withPortlet(ctx.getPortlet())
				.withThemeDisplay(ctx.getThemeDisplay())
				.withUser(ctx.getUser())
				.withLayout(ctx.getLayout())
				.withParameters(ParametersBuilder.builder().withParameter("name", "value").build())
				.build();
		ActionResponse resp = PortletResponseBuilder.newActionResponse()
				.withPortletRequest(req)
				.build();

		int count = EntryLocalServiceUtil.getEntriesCount();

		// addGuestbook reads parameters and creates a new Entry
		newPortlet.addGuestbook(req, resp);

		int count2 = EntryLocalServiceUtil.getEntriesCount();
		Assertions.assertThat(count2).isGreaterThan(count);
	}
}
```
