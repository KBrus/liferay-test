package com.github.kbrus.liferaytest.liferay.web.portlet;

import com.github.kbrus.liferaytest.liferay.web.servlet.TestHttpServletRequestImpl;
import com.liferay.portlet.ActionRequestImpl;

import java.lang.reflect.Field;

public class TestActionRequestImpl extends ActionRequestImpl
{
	private TestHttpServletRequestImpl request;

	public TestActionRequestImpl()
	{
		request = new TestHttpServletRequestImpl();

		try
		{
			// set the _request field to the request
			Field reqField = this.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("_request");
			reqField.setAccessible(true);
			reqField.set(this, request);
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
