package com.github.kbrus.liferaytest.liferay.web;

import javax.portlet.ActionResponse;
import javax.portlet.PortletResponse;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceResponse;

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
			// TODO: TestRenderResponseImpl
			super.portletResponse = renderResponse = null;
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
