package com.github.kbrus.liferaytest.liferay.web;

import com.github.kbrus.liferaytest.liferay.web.portlet.TestActionRequestImpl;
import com.github.kbrus.liferaytest.liferay.web.portlet.TestRenderRequestImpl;
import com.github.kbrus.liferaytest.liferay.web.servlet.TestHttpServletRequestImpl;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.portlet.*;
import javax.xml.namespace.QName;
import java.lang.reflect.Field;
import java.util.*;

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

		// TODO: Use PortletConfigImpl. PortletContext is required, pass it via param?
		portletRequest.setAttribute(JavaConstants.JAVAX_PORTLET_CONFIG, new LiferayPortletConfig()
		{
			@Override
			public Enumeration<Locale> getSupportedLocales()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ResourceBundle getResourceBundle(Locale locale)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<QName> getPublishingEventQNames()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<String> getPublicRenderParameterNames()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<QName> getProcessingEventQNames()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getPortletName()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<String> getInitParameterNames()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getInitParameter(String name)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getDefaultNamespace()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, String[]> getContainerRuntimeOptions()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isWARFile()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isCopyRequestParameters()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getPortletId()
			{
				// TODO Auto-generated method stub
				return portlet.getPortletId();
			}

			@Override
			public PortletContext getPortletContext()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Portlet getPortlet()
			{
				// TODO Auto-generated method stub
				return null;
			}
		});

		return this;
	}

	public PortletRequestBuilder<T> withLayout(Layout layout)
	{
		portletRequest.setAttribute(WebKeys.LAYOUT, layout);
		return this;
	}

	public PortletRequestBuilder<T> withThemeDisplay(ThemeDisplay themeDisplay)
	{
		portletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		return this;
	}

	public PortletRequestBuilder<T> withParameters(Map<String, Collection<String>> parameters)
	{
		try
		{
			Field field = portletRequest.getClass().getDeclaredField("request");
			field.setAccessible(true);
			TestHttpServletRequestImpl innerReq = (TestHttpServletRequestImpl) field.get(portletRequest);

			parameters.forEach((key, value) -> value
					.forEach(s -> innerReq.setParameter(key, s)));
			return this;
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
