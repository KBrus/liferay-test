package com.github.kbrus.liferaytest.liferay.web;

import com.github.kbrus.liferaytest.liferay.web.portlet.TestActionResponseImpl;
import com.github.kbrus.liferaytest.liferay.web.portlet.TestRenderResponseImpl;

import javax.portlet.*;
import java.lang.reflect.Field;

public abstract class PortletResponseBuilder<SELF extends PortletResponseBuilder<SELF, T>, T extends PortletResponse>
{
	private T portletResponse;

	public static ActionResponseBuilder newActionResponse()
	{
		return new ActionResponseBuilder();
	}

	public static RenderResponseBuilder newRenderResponse()
	{
		return new RenderResponseBuilder();
	}

	public static ResourceResponseBuilder newResourceResponse()
	{
		return new ResourceResponseBuilder();
	}

	@SuppressWarnings("unchecked")
	public SELF withPortletRequest(PortletRequest req)
			throws ReflectiveOperationException
	{
		Field field = portletResponse.getClass().getSuperclass().getDeclaredField("_portletRequestImpl");
		field.setAccessible(true);
		field.set(portletResponse, req);

		return (SELF) this;
	}

	public T build()
	{
		return portletResponse;
	}

	public static class ActionResponseBuilder extends PortletResponseBuilder<ActionResponseBuilder, ActionResponse>
	{
		private final ActionResponse actionResponse;

		private ActionResponseBuilder()
		{
			super.portletResponse = actionResponse = new TestActionResponseImpl();
		}

		@Override
		public ActionResponseBuilder withPortletRequest(PortletRequest req)
				throws ReflectiveOperationException
		{
			Field field = super.portletResponse.getClass().getSuperclass().getSuperclass().getDeclaredField("_portletRequestImpl");
			field.setAccessible(true);
			field.set(super.portletResponse, req);

			return this;
		}
	}

	public static class RenderResponseBuilder extends PortletResponseBuilder<RenderResponseBuilder, RenderResponse>
	{
		private final RenderResponse renderResponse;

		private RenderResponseBuilder()
		{
			super.portletResponse = renderResponse = new TestRenderResponseImpl();
		}
	}

	public static class ResourceResponseBuilder extends PortletResponseBuilder<ResourceResponseBuilder, ResourceResponse>
	{
		private final ResourceResponse resourceResponse;

		private ResourceResponseBuilder()
		{
			// TODO: TestResourceResponseImpl
			super.portletResponse = resourceResponse = null;
		}
	}
}
