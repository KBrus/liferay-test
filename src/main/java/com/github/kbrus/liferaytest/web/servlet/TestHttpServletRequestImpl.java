package com.github.kbrus.liferaytest.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

public class TestHttpServletRequestImpl implements HttpServletRequest
{
	private final Map<String, Object> attrs;
	private final Map<String, ArrayList<String>> params;
	private final Map<String, ArrayList<String>> headers;
	private HttpSession httpSession;

	public TestHttpServletRequestImpl()
	{
		attrs = new HashMap<>();
		params = new HashMap<>();
		headers = new HashMap<>();
	}

	public void setParameter(String name, String value)
	{
		if (!params.containsKey(name))
		{
			ArrayList<String> values = new ArrayList<>();
			values.add(value);
			params.put(name, values);
		}
		else
		{
			params.get(name).add(value);
		}
	}

	@Override
	public String getAuthType()
	{
		return null;
	}

	@Override
	public Cookie[] getCookies()
	{
		return new Cookie[0];
	}

	@Override
	public long getDateHeader(String name)
	{
		return 0;
	}

	@Override
	public String getHeader(String name)
	{
		ArrayList<String> values = headers.get(name);
		return values.stream().findFirst().orElse(null);
	}

	@Override
	public Enumeration<String> getHeaders(String name)
	{
		return Collections.enumeration(headers.get(name));
	}

	@Override
	public Enumeration<String> getHeaderNames()
	{
		return Collections.enumeration(headers.keySet());
	}

	@Override
	public int getIntHeader(String name)
	{
		return 0;
	}

	@Override
	public String getMethod()
	{
		return null;
	}

	@Override
	public String getPathInfo()
	{
		return null;
	}

	@Override
	public String getPathTranslated()
	{
		return null;
	}

	@Override
	public String getContextPath()
	{
		return null;
	}

	@Override
	public String getQueryString()
	{
		return null;
	}

	@Override
	public String getRemoteUser()
	{
		return null;
	}

	@Override
	public boolean isUserInRole(String role)
	{
		return false;
	}

	@Override
	public Principal getUserPrincipal()
	{
		return null;
	}

	@Override
	public String getRequestedSessionId()
	{
		return null;
	}

	@Override
	public String getRequestURI()
	{
		return null;
	}

	@Override
	public StringBuffer getRequestURL()
	{
		return null;
	}

	@Override
	public String getServletPath()
	{
		return null;
	}

	@Override
	public HttpSession getSession(boolean create)
	{
		if (create && httpSession == null) {
			httpSession = new TestHttpSessionImpl();
		}

		return httpSession;
	}

	@Override
	public HttpSession getSession()
	{
		return getSession(true);
	}

	@Override
	public boolean isRequestedSessionIdValid()
	{
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie()
	{
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL()
	{
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl()
	{
		return false;
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException
	{
		return false;
	}

	@Override
	public void login(String username, String password) throws ServletException
	{

	}

	@Override
	public void logout() throws ServletException
	{

	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException
	{
		return null;
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException
	{
		return null;
	}

	@Override
	public Object getAttribute(String name)
	{
		return attrs.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames()
	{
		return Collections.enumeration(attrs.keySet());
	}

	@Override
	public String getCharacterEncoding()
	{
		return null;
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException
	{

	}

	@Override
	public int getContentLength()
	{
		return 0;
	}

	@Override
	public String getContentType()
	{
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException
	{
		return null;
	}

	@Override
	public String getParameter(String name)
	{

		ArrayList<String> values = params.get(name);
		return values == null ? null : values.stream().findFirst().orElse(null);

		// this makes the request go further? wtf?!
		// return Arrays.stream(values).findFirst().orElse(null);
	}

	@Override
	public Enumeration<String> getParameterNames()
	{
		return Collections.enumeration(params.keySet());
	}

	@Override
	public String[] getParameterValues(String name)
	{
		return new String[0];
	}

	@Override
	public Map<String, String[]> getParameterMap()
	{
		Map<String, String[]> result = new HashMap<>();
		params.entrySet().stream().forEach(stringArrayListEntry -> {
			String[] array = new String[stringArrayListEntry.getValue().size()];
			stringArrayListEntry.getValue().toArray(array);
			result.put(stringArrayListEntry.getKey(), array);
		});
		return Collections.unmodifiableMap(result);
	}

	@Override
	public String getProtocol()
	{
		return null;
	}

	@Override
	public String getScheme()
	{
		return null;
	}

	@Override
	public String getServerName()
	{
		return null;
	}

	@Override
	public int getServerPort()
	{
		return 0;
	}

	@Override
	public BufferedReader getReader() throws IOException
	{
		return null;
	}

	@Override
	public String getRemoteAddr()
	{
		return null;
	}

	@Override
	public String getRemoteHost()
	{
		return null;
	}

	@Override
	public void setAttribute(String name, Object o)
	{
		attrs.put(name, o);
	}

	@Override
	public void removeAttribute(String name)
	{
		attrs.remove(name);
	}

	@Override
	public Locale getLocale()
	{
		return null;
	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return null;
	}

	@Override
	public boolean isSecure()
	{
		return false;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path)
	{
		return null;
	}

	@Override
	public String getRealPath(String path)
	{
		return null;
	}

	@Override
	public int getRemotePort()
	{
		return 0;
	}

	@Override
	public String getLocalName()
	{
		return null;
	}

	@Override
	public String getLocalAddr()
	{
		return null;
	}

	@Override
	public int getLocalPort()
	{
		return 0;
	}

	@Override
	public ServletContext getServletContext()
	{
		return null;
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException
	{
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException
	{
		return null;
	}

	@Override
	public boolean isAsyncStarted()
	{
		return false;
	}

	@Override
	public boolean isAsyncSupported()
	{
		return false;
	}

	@Override
	public AsyncContext getAsyncContext()
	{
		return null;
	}

	@Override
	public DispatcherType getDispatcherType()
	{
		return null;
	}
}
