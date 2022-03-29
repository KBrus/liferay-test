package com.github.kbrus.liferaytest.liferay.web;

import com.github.kbrus.liferaytest.web.servlet.TestHttpServletRequestImpl;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.bridges.mvc.MVCPortlet;

import javax.portlet.*;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public abstract class PortletRequestBuilder<SELF extends PortletRequestBuilder<SELF, T>, T extends PortletRequest>
{
	private T portletRequest;

	public static ActionRequestBuilder newActionRequest()
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

	@SuppressWarnings("unchecked")
	public SELF withUser(User user)
	{
		portletRequest.setAttribute(WebKeys.USER, user);
		portletRequest.setAttribute(WebKeys.COMPANY_ID, user.getCompanyId());
		return (SELF) this;
	}

	@SuppressWarnings("unchecked")
	public SELF withPortlet(MVCPortlet mvcPortlet, Portlet portletModel)
	{
		portletRequest.setAttribute(WebKeys.PORTLET_ID, portletModel.getPortletId());
		portletRequest.setAttribute(JavaConstants.JAVAX_PORTLET_CONFIG, mvcPortlet.getPortletConfig());

		return (SELF) this;
	}

	@SuppressWarnings("unchecked")
	public SELF withLayout(Layout layout)
	{
		portletRequest.setAttribute(WebKeys.LAYOUT, layout);
		return (SELF) this;
	}

	@SuppressWarnings("unchecked")
	public SELF withThemeDisplay(ThemeDisplay themeDisplay)
	{
		portletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		return (SELF) this;
	}

	@SuppressWarnings("unchecked")
	public SELF withParameters(Map<String, Collection<String>> parameters)
	{
		try
		{
			Field field = portletRequest.getClass().getDeclaredField("request");
			field.setAccessible(true);
			TestHttpServletRequestImpl innerReq = (TestHttpServletRequestImpl) field.get(portletRequest);

			parameters.forEach((key, value) -> value
					.forEach(s -> innerReq.setParameter(key, s)));
			return (SELF) this;
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	}

	public T build()
	{
		return portletRequest;
	}

	public static class ActionRequestBuilder extends PortletRequestBuilder<ActionRequestBuilder, ActionRequest>
	{
		private final ActionRequest actionRequest;

		private ActionRequestBuilder()
		{
			super.portletRequest = actionRequest = new TestActionRequestImpl();
		}
	}

	public static class RenderRequestBuilder extends PortletRequestBuilder<RenderRequestBuilder, RenderRequest>
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

	public static class ResourceRequestBuilder extends PortletRequestBuilder<ResourceRequestBuilder, ResourceRequest>
	{
		private final ResourceRequest resourceRequest;

		private ResourceRequestBuilder()
		{
			super.portletRequest = resourceRequest = null;
		}
	}

}
