package com.github.kbrus.liferaytest.liferay.util;

import com.github.kbrus.liferaytest.InitializationException;
import com.github.kbrus.liferaytest.liferay.bean.PortalBeanLocator;
import com.github.kbrus.liferaytest.liferay.bean.SpringContextBeanLocator;
import com.github.kbrus.liferaytest.liferay.configuration.TestConfigurationFactoryImpl;
import com.liferay.portal.cache.CacheRegistryImpl;
import com.liferay.portal.cache.key.SimpleCacheKeyGenerator;
import com.liferay.portal.configuration.ConfigurationImpl;
import com.liferay.portal.dao.db.DBFactoryImpl;
import com.liferay.portal.dao.jdbc.DataSourceFactoryImpl;
import com.liferay.portal.dao.jdbc.spring.MappingSqlQueryFactoryImpl;
import com.liferay.portal.dao.jdbc.spring.SqlUpdateFactoryImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.util.PropsFiles;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsUtil;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Builder-style portal startup util.
 * Call {@link #initializer()} to start building the initializer and run the portal with {@link #initialize()}
 */
public class PortalInitializer
{
	private boolean initializePortletContext;
	private String portletContextName;
	private String[] portletBasePackages;
	private LogFactory logFactory;
	private boolean createSchema;

	// should this even be thread safe?
	private static AtomicBoolean initialized = new AtomicBoolean(false);

	private PortalInitializer()
	{
	}

	public static PortalInitializer initializer()
	{
		return new PortalInitializer();
	}

	/**
	 * Sets which logger is used by the portal. If this method is not called, log4j is used.
	 * <br/><br/>
	 * Liferay uses Jdk14LogFactoryImpl by default (see LogFactoryUtil#_logFactory), and we don't want that.
	 *
	 * @param logFactory a class implementing {@link LogFactory}
	 * @return PortalInitializer for chaining
	 */
	public PortalInitializer withLogFactory(LogFactory logFactory)
	{
		this.logFactory = logFactory;
		return this;
	}

	/**
	 * Optionally set a portlet context name, for portlet bean discovery.
	 *
	 * @param portletContextName most likely something like <em>yourPortletName-portlet</em>
	 * @return PortalInitializer for chaining
	 */
	public PortalInitializer withPortletContext(String portletContextName)
	{
		this.portletContextName = portletContextName;
		return this;
	}

	/**
	 * Optionally set base portlet packages for portlet bean discovery.
	 *
	 * @param portletBasePackages a list of your portlet packages to scan
	 * @return PortalInitializer for chaining
	 */
	public PortalInitializer withPortletBasePackages(String... portletBasePackages)
	{
		this.initializePortletContext = true;
		this.portletBasePackages = portletBasePackages;
		return this;
	}

	/**
	 * Sets whether to run schema creation script when initializing. If tables already exist your logs will hate you.
	 *
	 * @param createSchema true/false
	 * @return PortalInitializer for chaining
	 */
	public PortalInitializer withCreateSchema(boolean createSchema)
	{
		this.initializePortletContext = true;
		this.createSchema = createSchema;
		return this;
	}

	public synchronized void initialize() throws InitializationException
	{
		if (initialized.get())
		{
			throw new InitializationException("Portal already initialized");
		}

		try
		{
			// Log4j gives nicer logs
			if (logFactory == null)
			{
				logFactory = new Log4jLogFactoryImpl();
			}
			LogFactoryUtil.setLogFactory(logFactory);

			initConfiguration();
			initDataSource();

			initBeanLocators(this.initializePortletContext, this.portletContextName, this.portletBasePackages);

			if (createSchema)
			{
				initSchema();
			}

			initialized.set(true);
		}
		catch (ReflectiveOperationException | SystemException e)
		{
			throw new InitializationException(e);
		}
	}

	private static void initConfiguration() throws ReflectiveOperationException
	{
		ConfigurationFactoryUtil.setConfigurationFactory(new TestConfigurationFactoryImpl());

		// PropsUtil construction will fail because it's unable to detect WebDir, but that's okay
		// since the exception is swallowed in the constructor. We still need the class to have
		// its _configuration field set though, since Model impls use it to set caching props
		Field propsUtilInstanceField = PropsUtil.class.getDeclaredField("_instance");
		Field propsUtilConfigurationField = PropsUtil.class.getDeclaredField("_configuration");
		propsUtilInstanceField.setAccessible(true);
		propsUtilConfigurationField.setAccessible(true);
		Object propsUtilInstance = propsUtilInstanceField.get(null);
		propsUtilConfigurationField.set(propsUtilInstance,
				new ConfigurationImpl(PropsUtil.class.getClassLoader(), PropsFiles.PORTAL));

		// oh look at that, two PropsUtils!
		// and what does this one do?
		// it calls the other one.
		Props props = new PropsImpl();
		com.liferay.portal.kernel.util.PropsUtil.setProps(props);

		// these need to be set
		new CacheKeyGeneratorUtil().setDefaultCacheKeyGenerator(new SimpleCacheKeyGenerator());
		CacheRegistryUtil.setCacheRegistry(new CacheRegistryImpl());

		// set up classloader - TransactionManagerFactory tries to get the portal classloader
		// and it's null if it's not set explicitly
		PortalClassLoaderUtil.setClassLoader(PortalInitializer.class.getClassLoader());
	}

	private static void initDataSource()
	{
		// these need to be set manually
		DataSourceFactoryUtil.setDataSourceFactory(new DataSourceFactoryImpl());
		DBFactoryUtil.setDBFactory(new DBFactoryImpl());
		new SqlUpdateFactoryUtil().setSqlUpdateFactory(new SqlUpdateFactoryImpl());
		new MappingSqlQueryFactoryUtil().setMappingSqlQueryFactory(new MappingSqlQueryFactoryImpl());
	}

	private static void initBeanLocators(boolean initializePortletContext, String portletContextName, String... portletBasePackages) throws InitializationException
	{
		PortalBeanLocatorUtil.setBeanLocator(new PortalBeanLocator());

		if (initializePortletContext)
		{

			if (ArrayUtil.isNotEmpty(portletBasePackages) && Validator.isNotNull(portletContextName))
			{
				// FIXME: this should use a different classloader
				PortletBeanLocatorUtil.setBeanLocator(portletContextName, new SpringContextBeanLocator(portletBasePackages));
			}
			else
			{
				throw new InitializationException("Portlet context name and base packages must be set for portlet initialization");
			}
		}
	}

	private static void initSchema() throws SystemException
	{
		// I think this is the only portal class that calls DB.runSQLTemplate with all the
		// portal sql files from com/liferay/portal/tools/sql/dependencies, but maybe
		// ConvertProcess or something from com.liferay.upgrade can be used instead?
		ReleaseLocalServiceUtil.createTablesAndPopulate();
	}

}
