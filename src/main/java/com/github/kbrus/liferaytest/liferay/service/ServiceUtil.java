package com.github.kbrus.liferaytest.liferay.service;

import java.lang.reflect.Field;

public class ServiceUtil
{
	public static void rewireService(Class<?> serviceUtilClass, Object service) throws ReflectiveOperationException
	{
		Field serviceField = serviceUtilClass.getDeclaredField("_service");
		serviceField.setAccessible(true);
		serviceField.set(null, service);
	}
}
