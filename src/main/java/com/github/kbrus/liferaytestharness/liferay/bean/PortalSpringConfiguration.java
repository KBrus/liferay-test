package com.github.kbrus.liferaytestharness.liferay.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = {
		"classpath:/META-INF/base-spring.xml",
		"classpath:/META-INF/counter-spring.xml",
		"classpath:/META-INF/infrastructure-spring.xml",
		"classpath:/META-INF/hibernate-spring.xml",
		"classpath:/META-INF/portal-spring.xml",
		"classpath:/META-INF/util-spring.xml"
})
public class PortalSpringConfiguration
{
//	@Bean(name = "com.liferay.counter.service.CounterLocalService")
//	public CounterLocalService counterLocalService(CounterFinder counterFinder)
//	{
//		CounterLocalServiceImpl service = new CounterLocalServiceImpl();
//
//		// TODO: Find out how finders are set. Via @BeanReference?
//		// service.setCounterFinder(counterFinder);
//
//		return service;
//	}
//
//	@Bean(name = "com.liferay.counter.service.persistence.CounterPersistence")
//	public CounterPersistence counterPersistence()
//	{
//		return new CounterPersistenceImpl();
//	}
//
//	@Bean(name = "com.liferay.counter.service.persistence.CounterFinder")
//	public CounterFinder counterFinder()
//	{
//		return new CounterFinderImpl();
//	}
}
