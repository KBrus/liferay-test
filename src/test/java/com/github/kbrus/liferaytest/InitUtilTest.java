package com.github.kbrus.liferaytest;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.util.InitUtil;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class InitUtilTest
{
	@Test
	public void whenInit_thenCounterServiceWired()
	{
		InitUtil.init();

		CounterLocalService service = CounterLocalServiceUtil.getService();
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	public void whenInitWithSpring_thenCounterServiceWired()
	{
		InitUtil.initWithSpring();

		CounterLocalService service = CounterLocalServiceUtil.getService();
		Assertions.assertThat(service).isNotNull();
	}
}
