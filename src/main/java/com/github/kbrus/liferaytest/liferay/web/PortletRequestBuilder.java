package com.github.kbrus.liferaytest.liferay.web;

import com.github.kbrus.liferaytest.liferay.web.portlet.TestActionRequestImpl;
import com.github.kbrus.liferaytest.liferay.web.portlet.TestRenderRequestImpl;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.portlet.*;

public abstract class PortletRequestBuilder<T extends PortletRequest>
{
	private T portletRequest;

	public static PortletRequestBuilder<ActionRequest> newActionRequest()
	{
		return new ActionRequestBuilder();
	}

	public static RenderRequestBuilder newRenderRequest()
	{
		return new RenderRequestBuilder();
	}

	public static ResourceRequestBuilder newResourceRequest()
	{
		return new ResourceRequestBuilder();
	}

	public PortletRequestBuilder<T> withUser(User user)
	{
		portletRequest.setAttribute(WebKeys.USER, user);
		portletRequest.setAttribute(WebKeys.COMPANY_ID, user.getCompanyId());
		return this;
	}

	public PortletRequestBuilder<T> withPortlet(Portlet portlet)
	{
		portletRequest.setAttribute(WebKeys.PORTLET_ID, portlet.getPortletId());
		return this;
	}

	public PortletRequestBuilder<T> withThemeDisplay(ThemeDisplay themeDisplay)
	{
		portletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		return this;
	}

	public T build()
	{
		return portletRequest;
	}

	public static class ActionRequestBuilder extends PortletRequestBuilder<ActionRequest>
	{
		private final ActionRequest actionRequest;

		private ActionRequestBuilder()
		{
			super.portletRequest = actionRequest = new TestActionRequestImpl();
		}
	}

	public static class RenderRequestBuilder extends PortletRequestBuilder<RenderRequest>
	{
		private final TestRenderRequestImpl renderRequest;

		private RenderRequestBuilder()
		{
			super.portletRequest = renderRequest = new TestRenderRequestImpl();
		}

		public RenderRequestBuilder withPortletMode(PortletMode portletMode)
		{
			renderRequest.setPortletMode(portletMode);
			return this;
		}

		public RenderRequestBuilder withWindowState(WindowState windowState)
		{
			renderRequest.setWindowState(windowState);
			return this;
		}

		@Override
		public RenderRequest build()
		{
			// TODO: Validation maybe?
			return super.build();
		}
	}

	public static class ResourceRequestBuilder extends PortletRequestBuilder<ResourceRequest>
	{
		private final ResourceRequest resourceRequest;

		private ResourceRequestBuilder()
		{
			super.portletRequest = resourceRequest = null;
		}
	}

}
