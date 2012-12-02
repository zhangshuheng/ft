package org.wl.core.security.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

public class LoginFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

	private final static Log log = LogFactory.getLog(PasswordCheckDaoAuthenticationProvider.class);

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.debug("onAuthenticationFailure");
		super.onAuthenticationFailure(request, response, exception);
	}

}
