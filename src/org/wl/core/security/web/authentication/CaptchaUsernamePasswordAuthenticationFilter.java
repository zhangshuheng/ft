package org.wl.core.security.web.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

@SuppressWarnings("unused")
public class CaptchaUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private CaptchaService captchaService;
	private String captchaParamterName = "captcha";

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//		if (validateCaptchaChallenge(request) == false) {
//		//	throw new AuthenticationServiceException("验证码错误");
//		}
		return super.attemptAuthentication(request, response);
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	/**
	 * 验证验证码
	 * 
	 * @param request
	 * @return
	 */
	protected boolean validateCaptchaChallenge(HttpServletRequest request) {
		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = request.getParameter(this.captchaParamterName);
			return this.captchaService.validateResponseForID(captchaID, challengeResponse).booleanValue();
		} catch (CaptchaServiceException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
}