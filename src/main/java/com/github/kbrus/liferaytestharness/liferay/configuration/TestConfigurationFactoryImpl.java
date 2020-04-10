package com.github.kbrus.liferaytestharness.liferay.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactory;

import java.io.IOException;

public class TestConfigurationFactoryImpl implements ConfigurationFactory
{
	@Override
	public Configuration getConfiguration(ClassLoader classLoader, String name)
	{
		try
		{
			if (name.equalsIgnoreCase("portlet") || name.equalsIgnoreCase("service"))
			{
				return new TestConfigurationImpl();
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		throw new UnsupportedOperationException("Scopes " + name + " is not supported");
	}
}
