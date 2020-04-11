package com.github.kbrus.liferaytestharness.liferay.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;

import java.util.Map;

public class ManualPortletBeanLocator implements BeanLocator
{
	@Override
	public ClassLoader getClassLoader()
	{
		return null;
	}

	@Override
	public String[] getNames()
	{
		return new String[0];
	}

	@Override
	public Class<?> getType(String name) throws BeanLocatorException
	{
		return null;
	}

	@Override
	public <T> Map<String, T> locate(Class<T> clazz) throws BeanLocatorException
	{
		return null;
	}

	@Override
	public Object locate(String name) throws BeanLocatorException
	{
		return null;
	}
}
