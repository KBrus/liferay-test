package com.github.kbrus.liferaytest.liferay.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.util.InitUtil;

public class PortalInitializer
{
	public static void initialize() throws SystemException
	{
		InitUtil.initWithSpring();
		ReleaseLocalServiceUtil.createTablesAndPopulate();
	}
}
