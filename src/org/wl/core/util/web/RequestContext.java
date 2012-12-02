package org.wl.core.util.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RequestContext implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CONTAINER = "_CONTAINER";

	public static final String RESULT_INFO = "_RESULT_INFO";

	private static ThreadLocal<RequestContext> threadLocal = new ThreadLocal<RequestContext>();


	private static final String HTTP_REQUEST = "http.request";

	private static final String HTTP_RESPONSE = "http.response";

	private static final String PARAMETERS = "map.parameters";

	private static final String SESSION = "map.session";


	private Map<String, Object> context;

	public static RequestContext getContext(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = new HashMap<String,Object>();
		context.put(HTTP_REQUEST, request);
		context.put(HTTP_RESPONSE, response);
		context.put(PARAMETERS, request.getParameterMap());
		context.put(SESSION, request.getSession());
		RequestContext requestContext = new RequestContext(context);
		threadLocal.set(requestContext);
		return requestContext;
	}


	public static ServletContext getServletContext(HttpServletRequest request) {
		return request.getSession().getServletContext();
	}

	public RequestContext(Map<String, Object> context) {
		this.context = context;
	}

	/**
	 * 获取 Action 上下
	 * 
	 * @return
	 */
	public static RequestContext getContext() {
		RequestContext context = (RequestContext) threadLocal.get();
		return context;
	}

	/**
	 * 获取request中的Parameters
	 * 
	 * @param parameters
	 */
	public Map<String, String[]> getParameters() {
		return (Map<String, String[]>) get(PARAMETERS);
	}

	/**
	 * 获取 ActionSession
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return (HttpSession) get(SESSION);
	}


	public Object get(Object key) {
		return context.get(key);
	}

	public void put(String key, Object value) {
		context.put(key, value);
	}

	public String getIpAddr() {
		HttpServletRequest request = getHttpRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public HttpServletRequest getHttpRequest() {
		return (HttpServletRequest) context.get(HTTP_REQUEST);
	}

	public HttpServletResponse getHttpResponse() {
		return (HttpServletResponse) context.get(HTTP_RESPONSE);
	}
}
