package com.github.kbrus.liferaytestharness.liferay.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.spring.context.PortalApplicationContext;

import java.util.Map;

public class PortalBeanLocator implements BeanLocator
{
	private PortalApplicationContext applicationContext;

	public PortalBeanLocator()
	{
		applicationContext = new PortalApplicationContext();
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
