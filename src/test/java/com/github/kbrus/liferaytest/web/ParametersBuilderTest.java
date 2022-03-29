package com.github.kbrus.liferaytest.web;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

class ParametersBuilderTest
{
	@Test
	@DisplayName("Params are not namespaced by default")
	public void whenWithParameter_thenNoNamespaceAdded()
	{
		Map<String, Collection<String>> params = ParametersBuilder.builder()
				.withParameter("key", "value")
				.build();

		Assertions.assertThat(params).containsKey("key");
	}

	@Test
	@DisplayName("Namespacing parameters adds a key prefix")
	public void whenNamespaced_thenNamespaceAdded()
	{
		Map<String, Collection<String>> params = ParametersBuilder.builder()
				.namespaced("prefix")
				.withParameter("key", "value")
				.build();

		Assertions.assertThat(params).containsKey("prefix_key");
	}

	@Test
	@DisplayName("Un-namespacing parameters doesn't add a key prefix")
	public void whenUnnamespaced_thenNoNamespaceAdded()
	{
		Map<String, Collection<String>> params = ParametersBuilder.builder()
				.namespaced("prefix")
				.withParameter("key", "value")
				.unnamespaced()
				.withParameter("key2", "value2")
				.build();

		Assertions.assertThat(params).containsKey("key2");
	}

	@Test
	@DisplayName("Multiple values can be stored under a key")
	public void whenWithParameter_thenMultipleValuesStored()
	{
		Map<String, Collection<String>> params = ParametersBuilder.builder()
				.withParameter("key", "value1")
				.withParameter("key", "value2")
				.build();

		Assertions.assertThat(params)
				.hasEntrySatisfying(
						"key",
						new Condition<>(strings -> strings.contains("value1") && strings.contains("value2"),
								"Parameter value must contain all add values"));
	}
}