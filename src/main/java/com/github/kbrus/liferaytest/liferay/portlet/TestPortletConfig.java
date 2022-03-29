package com.github.kbrus.liferaytest.liferay.portlet;

import org.apache.commons.lang.NotImplementedException;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.xml.namespace.QName;
import java.util.*;

public class TestPortletConfig implements PortletConfig
{
	private final PortletContext portletContext;
	private final ResourceBundle resourceBundle;

	public TestPortletConfig()
	{
		this(new TestPortletContext("dummy"));
	}

	public TestPortletConfig(PortletContext portletContext)
	{
		this.portletContext = portletContext;
		resourceBundle = new ResourceBundle()
		{
			@Override
			protected Object handleGetObject(String key)
			{
				switch (key)
				{
					case "javax.portlet.title":
						return "Test";
					default:
						throw new NotImplementedException();
				}
			}

			@Override
			public Enumeration<String> getKeys()
			{
				return Collections.emptyEnumeration();
			}
		};
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
		return resourceBundle;
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
