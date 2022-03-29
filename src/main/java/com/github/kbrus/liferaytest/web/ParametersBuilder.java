package com.github.kbrus.liferaytest.web;

import com.liferay.portal.kernel.util.StringPool;

import java.util.*;

public class ParametersBuilder
{
	private String namespace;
	private final Map<String, Collection<String>> map;

	private ParametersBuilder()
	{
		namespace = StringPool.BLANK;
		map = new HashMap<>();
	}

	public static ParametersBuilder builder()
	{
		return new ParametersBuilder();
	}

	public ParametersBuilder namespaced(String namespace)
	{
		this.namespace = namespace;
		return this;
	}

	public ParametersBuilder unnamespaced()
	{
		namespace = StringPool.BLANK;
		return this;
	}

	public ParametersBuilder withParameter(String name, String value)
	{
		name = StringPool.BLANK.equals(namespace) ? name : (namespace + "_" + name);

		if (!map.containsKey(name))
		{
			List<String> list = new ArrayList<>();
			list.add(value);
			map.put(name, list);
		}
		else
		{
			map.get(name).add(value);
		}

		return this;
	}

	public Map<String, Collection<String>> build()
	{
		return map;
	}
}
