package com.github.kbrus.liferaytest.liferay.web.servlet;

import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.*;

public class TestHttpSessionImpl implements HttpSession
{
	private final long creationTime;
	private final String id;
	private final Map<String, Object> attrs;

	public TestHttpSessionImpl()
	{
		creationTime = System.currentTimeMillis();
		id = UUID.randomUUID().toString();
		attrs = new HashMap<>();
	}

	@Override
	public long getCreationTime()
	{
		return creationTime;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public long getLastAccessedTime()
	{
		return 0;
	}

	@Override
	public ServletContext getServletContext()
	{
		return null;
	}

	@Override
	public void setMaxInactiveInterval(int interval)
	{

	}

	@Override
	public int getMaxInactiveInterval()
	{
		return 0;
	}

	@Override
	public HttpSessionContext getSessionContext()
	{
		return null;
	}

	@Override
	public Object getAttribute(String name)
	{
		return attrs.get(name);
	}

	@Override
	public Object getValue(String name)
	{
		return getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames()
	{
		return Collections.enumeration(attrs.keySet());
	}

	@Override
	public String[] getValueNames()
	{
		Set<String> keySet = attrs.keySet();
		String[] names = new String[keySet.size()];
		return keySet.toArray(names);
	}

	@Override
	public void setAttribute(String name, Object value)
	{
		attrs.put(name, value);
	}

	@Override
	public void putValue(String name, Object value)
	{
		setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name)
	{
		attrs.remove(name);
	}

	@Override
	public void removeValue(String name)
	{
		removeAttribute(name);
	}

	@Override
	public void invalidate()
	{

	}

	@Override
	public boolean isNew()
	{
		return false;
	}
}
