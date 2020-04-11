package com.github.kbrus.liferaytest.liferay.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class SpringContextBeanLocator implements BeanLocator
{
	private final AnnotationConfigApplicationContext applicationContext;

	public SpringContextBeanLocator(String... basePackages)
	{
		applicationContext = new AnnotationConfigApplicationContext(basePackages);
	}

	public SpringContextBeanLocator(Class<?>... annotatedClasses)
	{
		applicationContext = new AnnotationConfigApplicationContext(annotatedClasses);
	}

	@Override
	public ClassLoader getClassLoader()
	{
		return applicationContext.getClass().getClassLoader();
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
