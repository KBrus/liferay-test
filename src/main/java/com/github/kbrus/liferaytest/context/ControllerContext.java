package com.github.kbrus.liferaytest.context;

import com.github.kbrus.liferaytest.liferay.bean.PortletBeanLocator;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.*;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.service.*;
import com.liferay.portal.service.impl.PortletLocalServiceImpl;
import com.liferay.portal.service.persistence.*;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.PortalUtil;
import org.apache.commons.io.FileUtils;

import javax.naming.NamingException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;

public class ControllerContext
{
	private final User user;
	private final ThemeDisplay themeDisplay;
	private final Layout layout;
	private final Portlet portlet;

	public ControllerContext() throws Exception
	{
		long companyId = CounterLocalServiceUtil.increment();
		long companyGroupId = CounterLocalServiceUtil.increment();
		long userGroupId = CounterLocalServiceUtil.increment();
		long accountId = CounterLocalServiceUtil.increment();
		long userId = CounterLocalServiceUtil.increment();
		long contactId = CounterLocalServiceUtil.increment();
		long plid = CounterLocalServiceUtil.increment();
		String portletId = StringUtil.randomString();

		Company company = CompanyUtil.create(companyId);
		company.setAccountId(accountId);
		CompanyLocalServiceUtil.updateCompany(company);

		user = UserUtil.create(userId);
		user.setCompanyId(companyId);
		user.setContactId(contactId);
		user.setEmailAddress(StringUtil.randomString(8) + "@" + StringUtil.randomString(4) + "." + StringUtil.randomString(2));
		user.setScreenName(StringUtil.randomString(8));
		UserLocalServiceUtil.updateUser(user);

		Group companyGroup = GroupUtil.create(companyGroupId);
		companyGroup.setCompanyId(companyId);
		companyGroup.setClassPK(companyId);
		companyGroup.setClassNameId(PortalUtil.getClassNameId(Company.class));
		companyGroup.setName("TestCompanyGroup"); // IX_BBCA55B
		companyGroup.setFriendlyURL("/TestCompanyGroup"); // IX_5BDDB872
		GroupLocalServiceUtil.updateGroup(companyGroup);

		Group userGroup = GroupUtil.create(userGroupId);
		userGroup.setCompanyId(companyId);
		userGroup.setClassPK(userId);
		userGroup.setClassNameId(PortalUtil.getClassNameId(User.class));
		userGroup.setName(user.getScreenName()); // IX_BBCA55B
		userGroup.setFriendlyURL("/" + user.getScreenName()); // IX_5BDDB872
		GroupLocalServiceUtil.updateGroup(userGroup);

		Account account = AccountUtil.create(accountId);
		account.setCompanyId(companyId);
		AccountLocalServiceUtil.updateAccount(account);

		Contact contact = ContactUtil.create(contactId);
		ContactLocalServiceUtil.updateContact(contact);

		layout = LayoutUtil.create(plid);
		layout.setCompanyId(companyId);
		layout.setGroupId(companyGroupId);
		LayoutLocalServiceUtil.updateLayout(layout);

		themeDisplay = ThemeDisplayFactory.create();
		themeDisplay.setCompany(company);
		themeDisplay.setUser(user);

		PortletApp portletApp = new PortletAppImpl("test");
		portlet = PortletUtil.create(CounterLocalServiceUtil.increment());
		portlet.setPortletApp(portletApp);
		portlet.setPortletId(portletId);
		portlet.setCompanyId(companyId);
		PortletLocalServiceUtil.updatePortlet(portlet);

		// add portlet to portlet pool
		// this is normally done by MainServlet during init() (MainServlet:866)
		// all services are also JDK dynamic proxies, which could be checked with:
		// * AopUtils.isJdkDynamicProxy(PortletLocalServiceUtil.getService());
		// * Proxy.isProxyClass(PortletLocalServiceUtil.getService().getClass());
		// InvocationHandler invocationHandler = Proxy.getInvocationHandler(PortletLocalServiceUtil.getService());
		PortletLocalServiceImpl portletLocalServiceImpl = (PortletLocalServiceImpl)
				ServiceBeanAopProxy.getAdvisedSupport(PortletLocalServiceUtil.getService())
						.getTargetSource().getTarget();

		Field declaredField = portletLocalServiceImpl.getClass().getDeclaredField("_portletsPool");
		declaredField.setAccessible(true);
		Map<String, Portlet> portletPool = (Map<String, Portlet>) declaredField.get(null);
		portletPool.put(portletId, portlet);
	}

	public ThemeDisplay getThemeDisplay()
	{
		return themeDisplay;
	}

	public User getUser()
	{
		return user;
	}

	public Layout getLayout()
	{
		return layout;
	}

	public Portlet getPortlet()
	{
		return portlet;
	}

	public void initializePortletServices(String portletContextName) throws IOException, SQLException, NamingException
	{
		PortletClassLoaderUtil.setClassLoader(Thread.currentThread().getContextClassLoader());
		PortletBeanLocator portletBeanLocator = new PortletBeanLocator();
		PortletBeanLocatorUtil.setBeanLocator(portletContextName, portletBeanLocator);

		// init portlet tables - will loudly complain about existing tables
		Path sqlPath = Paths.get(System.getProperty("user.dir"), "docroot", "WEB-INF", "sql");
		String tablesTemplate = FileUtils.readFileToString(sqlPath.resolve("tables.sql").toFile());
		String indexesTemplate = FileUtils.readFileToString(sqlPath.resolve("indexes.sql").toFile());
		String sequencesTemplate = FileUtils.readFileToString(sqlPath.resolve("sequences.sql").toFile());

		DB db = DBFactoryUtil.getDB();
		db.runSQLTemplateString(tablesTemplate, false, false);
		db.runSQLTemplateString(indexesTemplate, false, false);
		db.runSQLTemplateString(sequencesTemplate, false, false);
	}
}
