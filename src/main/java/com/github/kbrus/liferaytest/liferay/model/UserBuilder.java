package com.github.kbrus.liferaytest.liferay.model;

import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;

public class UserBuilder
{
	private final User user;

	private UserBuilder(long userId)
	{
		user = new UserImpl();
		user.setUserId(userId);
	}

	public UserBuilder withName(String firstName, String lastName)
	{
		user.setFirstName(firstName);
		user.setLastName(lastName);
		return this;
	}

	public UserBuilder withEmail(String email)
	{
		user.setEmailAddress(email);
		return this;
	}

	public UserBuilder withScreenName(String screenName)
	{
		user.setScreenName(screenName);
		return this;
	}

	public User build()
	{
		return user;
	}

	public static UserBuilder newUser(long userId)
	{
		return new UserBuilder(userId);
	}
}
