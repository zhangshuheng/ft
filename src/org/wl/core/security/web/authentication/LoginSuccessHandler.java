package org.wl.core.security.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import org.wl.core.security.listener.LoginSessionBindListener;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		System.out.print("LoginSuccess");
		super.onAuthenticationSuccess(request, response, authentication);
		request.getSession().setAttribute("loginSessionBindListener", new LoginSessionBindListener());
	}

}
