package com.github.kbrus.liferaytest.liferay.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfigurationImpl implements Configuration
{
	private final static String CONFIG_FILE_NAME = "portlet-test.properties";

	private final Properties props;

	public TestConfigurationImpl() throws IOException
	{
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
		props = new Properties();
		props.load(stream);
	}

	@Override
	public void addProperties(Properties properties)
	{
		throw new UnsupportedOperationException("Adding properties is not supported yet");
	}

	@Override
	public void clearCache()
	{
		throw new UnsupportedOperationException("Caching properties is not supported yet");
	}

	@Override
	public boolean contains(String key)
	{
		return props.containsKey(key);
	}

	@Override
	public String get(String key)
	{
		return props.getProperty(key);
	}

	@Override
	public String get(String key, Filter filter)
	{
		throw new UnsupportedOperationException("Filtering properties is not supported yet");
	}

	@Override
	public String[] getArray(String key)
	{
		return props.getProperty(key, StringPool.BLANK).split(",");
	}

	@Override
	public String[] getArray(String key, Filter filter)
	{
		throw new UnsupportedOperationException("Filtering properties is not supported yet");
	}

	@Override
	public Properties getProperties()
	{
		return props;
	}

	@Override
	public Properties getProperties(String prefix, boolean removePrefix)
	{
		throw new UnsupportedOperationException("Filtering properties is not supported yet");
	}

	@Override
	public void removeProperties(Properties properties)
	{
		throw new UnsupportedOperationException("Removing properties is not supported yet");
	}

	@Override
	public void set(String key, String value)
	{
		props.setProperty(key, value);
	}
}
