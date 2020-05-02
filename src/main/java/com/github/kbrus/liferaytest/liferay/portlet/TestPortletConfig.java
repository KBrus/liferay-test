package com.github.kbrus.liferaytest.liferay.portlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.xml.namespace.QName;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class TestPortletConfig implements PortletConfig
{
	private final PortletContext portletContext;

	public TestPortletConfig()
	{
		this(new TestPortletContext("dummy"));
	}

	public TestPortletConfig(PortletContext portletContext)
	{
		this.portletContext = portletContext;
	}

	@Override
	public String getPortletName()
	{
		return null;
	}

	@Override
	public PortletContext getPortletContext()
	{
		return portletContext;
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale)
	{
		return null;
	}

	@Override
	public String getInitParameter(String name)
	{
		return null;
	}

	@Override
	public Enumeration<String> getInitParameterNames()
	{
		return null;
	}

	@Override
	public Enumeration<String> getPublicRenderParameterNames()
	{
		return null;
	}

	@Override
	public String getDefaultNamespace()
	{
		return null;
	}

	@Override
	public Enumeration<QName> getPublishingEventQNames()
	{
		return null;
	}

	@Override
	public Enumeration<QName> getProcessingEventQNames()
	{
		return null;
	}

	@Override
	public Enumeration<Locale> getSupportedLocales()
	{
		return null;
	}

	@Override
	public Map<String, String[]> getContainerRuntimeOptions()
	{
		return null;
	}
}
