package com.github.kbrus.liferaytest.liferay.portlet;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

public class TestPortletContext implements PortletContext
{
	private final String servletContextName;

	public TestPortletContext(String servletContextName)
	{
		this.servletContextName = servletContextName;
	}

	@Override
	public String getServerInfo()
	{
		return null;
	}

	@Override
	public PortletRequestDispatcher getRequestDispatcher(String path)
	{
		return null;
	}

	@Override
	public PortletRequestDispatcher getNamedDispatcher(String name)
	{
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String path)
	{
		return null;
	}

	@Override
	public int getMajorVersion()
	{
		return 0;
	}

	@Override
	public int getMinorVersion()
	{
		return 0;
	}

	@Override
	public String getMimeType(String file)
	{
		return null;
	}

	@Override
	public String getRealPath(String path)
	{
		return null;
	}

	@Override
	public Set<String> getResourcePaths(String path)
	{
		return null;
	}

	@Override
	public URL getResource(String path) throws MalformedURLException
	{
		return null;
	}

	@Override
	public Object getAttribute(String name)
	{
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames()
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
	public void log(String msg)
	{

	}

	@Override
	public void log(String message, Throwable throwable)
	{

	}

	@Override
	public void removeAttribute(String name)
	{

	}

	@Override
	public void setAttribute(String name, Object object)
	{

	}

	@Override
	public String getPortletContextName()
	{
		return servletContextName;
	}

	@Override
	public Enumeration<String> getContainerRuntimeOptions()
	{
		return null;
	}
}
