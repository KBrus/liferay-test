package com.github.kbrus.liferaytest.liferay.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.spring.context.PortletBeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class PortletBeanLocator implements BeanLocator
{
	final ClassPathXmlApplicationContext applicationContext;

	public PortletBeanLocator()
	{
		String[] configLocations = {
				"META-INF/base-spring.xml",
				"META-INF/hibernate-spring.xml",
				"META-INF/infrastructure-spring.xml",
				"META-INF/cluster-spring.xml",
				"META-INF/portlet-spring.xml",
				"META-INF/shard-data-source-spring.xml"//,
				// "META-INF/ext-spring.xml"
		};
		applicationContext = new ClassPathXmlApplicationContext(configLocations, false);
		applicationContext.addBeanFactoryPostProcessor(new PortletBeanFactoryPostProcessor());
		applicationContext.refresh();
	}

	@Override
	public ClassLoader getClassLoader()
	{
		return applicationContext.getClassLoader();
	}

	@Override
	public String[] getNames()
	{
		return applicationContext.getBeanDefinitionNames();
	}

	@Override
	public Class<?> getType(String name) throws BeanLocatorException
	{
		return applicationContext.getType(name);
	}

	@Override
	public <T> Map<String, T> locate(Class<T> clazz) throws BeanLocatorException
	{
		return applicationContext.getBeansOfType(clazz);
	}

	@Override
	public Object locate(String name) throws BeanLocatorException
	{
		return applicationContext.getBean(name);
	}
}
