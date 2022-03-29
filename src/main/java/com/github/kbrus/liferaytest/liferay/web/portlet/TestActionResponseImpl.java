package com.github.kbrus.liferaytest.liferay.web.portlet;

import com.github.kbrus.liferaytest.liferay.web.servlet.TestHttpServletResponseImpl;
import com.liferay.portlet.ActionResponseImpl;

import java.lang.reflect.Field;

public class TestActionResponseImpl extends ActionResponseImpl
{
	private final TestHttpServletResponseImpl response;

	public TestActionResponseImpl()
	{
		response = new TestHttpServletResponseImpl();

		try
		{
			// set the _response field to the request
			Field field = this.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("_response");
			field.setAccessible(true);
			field.set(this, response);
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
