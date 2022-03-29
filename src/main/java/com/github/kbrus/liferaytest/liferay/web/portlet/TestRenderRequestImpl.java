package com.github.kbrus.liferaytest.liferay.web.portlet;

import com.github.kbrus.liferaytest.liferay.web.servlet.TestHttpServletRequestImpl;
import com.liferay.portlet.RenderRequestImpl;

import java.lang.reflect.Field;

public class TestRenderRequestImpl extends RenderRequestImpl
{
	private final TestHttpServletRequestImpl request;

	public TestRenderRequestImpl()
	{
		request = new TestHttpServletRequestImpl();

		try
		{
			// set the _request field to the request
			Field reqField = this.getClass().getSuperclass().getSuperclass().getDeclaredField("_request");
			reqField.setAccessible(true);
			reqField.set(this, request);
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
