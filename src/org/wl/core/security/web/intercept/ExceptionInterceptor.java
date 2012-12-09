package org.wl.core.security.web.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.wl.core.util.common.ObjectUtil;

/**
 * 用于Web访问时,异常处理流程
 * 
 * @author 李茂峰
 * 
 */
public class ExceptionInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(ExceptionInterceptor.class);

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object args, Exception exception) throws Exception {
		if (ObjectUtil.isNotNull(exception)) {
			logger.error(exception.getMessage(), exception);
			request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
			int status = 500;
			if (exception instanceof AuthenticationException) {
				status = 401;
				// } else if (exception instanceof AiaIspException &&
				// "true".equals(request.getParameter("isAjax"))) {
				// status = 400;
			} else {
				status = 500;
			}
			response.sendError(status);
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object args, ModelAndView andView) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object args) throws Exception {
		return true;
	}

}
