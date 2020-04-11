package com.github.kbrus.liferaytest;

public class InitializationException extends Exception
{
	public InitializationException()
	{
	}

	public InitializationException(String s)
	{
		super(s);
	}

	public InitializationException(String s, Throwable throwable)
	{
		super(s, throwable);
	}

	public InitializationException(Throwable throwable)
	{
		super(throwable);
	}

	public InitializationException(String s, Throwable throwable, boolean b, boolean b1)
	{
		super(s, throwable, b, b1);
	}
}
