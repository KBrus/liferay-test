package com.github.kbrus.liferaytestharness.liferay.web.portlet;

import com.liferay.portlet.RenderRequestImpl;
import com.github.kbrus.liferaytestharness.liferay.web.servlet.TestHttpServletRequestImpl;

import java.lang.reflect.Field;

public class TestRenderRequestImpl extends RenderRequestImpl
{
	private TestHttpServletRequestImpl request;

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
