package org.wl.core.security.listener;


import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContext;

public class LoginSessionBindListener implements HttpSessionBindingListener {

	private final static Log log = LogFactory.getLog(LoginSessionBindListener.class);

	/**
	 * 登录成功后，会触发这个地方，可以向系统中写入登录记录
	 */
	public void valueBound(HttpSessionBindingEvent bindingEvent) {
		log.debug("bound LoginSessionBindListener");
	}

	/**
	 * 注销操作时，只需要session remove SPRING_SECURITY_CONTEXT,就会触发这个地方，可以做一些其他的注销操作
	 * 可以从系统中删除登录记录
	 */
	public void valueUnbound(HttpSessionBindingEvent bindingEvent) {
		@SuppressWarnings("unused")
		SecurityContext securityContext = (SecurityContext) bindingEvent.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		log.debug("unbound LoginSessionBindListener");
	}

}
