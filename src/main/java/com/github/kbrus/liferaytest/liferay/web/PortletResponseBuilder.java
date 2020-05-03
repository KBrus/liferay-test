package com.github.kbrus.liferaytest.liferay.web;

import com.github.kbrus.liferaytest.liferay.web.portlet.TestRenderResponseImpl;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.*;
import java.lang.reflect.Field;

public abstract class PortletResponseBuilder<T extends PortletResponse>
{
	private T portletResponse;

	public static PortletResponseBuilder<ActionResponse> newActionResponse()
	{
		return new ActionResponseBuilder();
	}

	public static PortletResponseBuilder<RenderResponse> newRenderResponse()
	{
		return new RenderResponseBuilder();
	}

	public static PortletResponseBuilder<ResourceResponse> newResourceResponse()
	{
		return new ResourceResponseBuilder();
	}

	public PortletResponseBuilder<T> withPortletRequest(PortletRequest req)
			throws ReflectiveOperationException
	{
		Field field = portletResponse.getClass().getSuperclass().getDeclaredField("_portletRequestImpl");
		field.setAccessible(true);
		field.set(portletResponse, req);

		return this;
	}

	public T build()
	{
		return portletResponse;
	}

	public static class ActionResponseBuilder extends PortletResponseBuilder<ActionResponse>
	{
		private final ActionResponse actionResponse;

		private ActionResponseBuilder()
		{
			// TODO: TestActionResponseImpl
			super.portletResponse = actionResponse = null;
		}
	}

	public static class RenderResponseBuilder extends PortletResponseBuilder<RenderResponse>
	{
		private final RenderResponse renderResponse;

		private RenderResponseBuilder()
		{
			super.portletResponse = renderResponse = new TestRenderResponseImpl();
		}
	}

	public static class ResourceResponseBuilder extends PortletResponseBuilder<ResourceResponse>
	{
		private final ResourceResponse resourceResponse;

		private ResourceResponseBuilder()
		{
			// TODO: TestResourceResponseImpl
			super.portletResponse = resourceResponse = null;
		}
	}
}
