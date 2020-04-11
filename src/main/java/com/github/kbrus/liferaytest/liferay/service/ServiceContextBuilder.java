package com.github.kbrus.liferaytest.liferay.service;

import com.github.kbrus.liferaytest.liferay.web.PortletRequestBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;

import javax.portlet.PortletRequest;

public class ServiceContextBuilder
{
	private ServiceContext serviceContext;

	private ServiceContextBuilder()
	{
		serviceContext = new ServiceContext();
	}

	public ServiceContextBuilder withRequest(PortletRequest request)
	{
		try
		{
			serviceContext = ServiceContextFactory.getInstance(request);
			return this;
		}
		catch (PortalException | SystemException e)
		{
			throw new RuntimeException("Error creating ServiceContext from PortletRequest", e);
		}
	}

	public ServiceContext build()
	{
		return serviceContext;
	}

	/**
	 * Creates a new builder for an empty ServiceContext
	 * @return ServiceContextBuilder for chaining
	 */
	public static ServiceContextBuilder newServiceContext()
	{
		return new ServiceContextBuilder();
	}

	/**
	 * Creates a new builder and initializes ServiceContext to values from PortletRequest.
	 * @param request use {@linkplain PortletRequestBuilder}
	 * @return ServiceContextBuilder for chaining
	 */
	public static ServiceContextBuilder newServiceContext(PortletRequest request)
	{
		return new ServiceContextBuilder()
				.withRequest(request);
	}
}
