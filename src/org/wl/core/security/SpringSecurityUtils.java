package org.wl.core.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.wl.core.security.userdetails.SimpleUser;
import org.wl.core.spring.SpringContextUtil;
import org.wl.core.util.reflect.ClassUtil;

/**
 * Spring Security 的帮助类
 * @author 李茂峰
 *
 */
public class SpringSecurityUtils {	
	
	public static SimpleUser getCurrentUser() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if ((principal instanceof UserDetails)) {
				return (SimpleUser) principal;
			}
		}
		return null;
	}

	public static String getCurrentUserName() {
		Authentication authentication = getAuthentication();
		if ((authentication != null) && (authentication.getPrincipal() != null)) {
			return authentication.getName();
		}
		return "";
	}

	public static String getCurrentUserIp() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			Object details = authentication.getDetails();
			if ((details instanceof WebAuthenticationDetails)) {
				WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
				return webDetails.getRemoteAddress();
			}
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public static boolean hasAnyAuthority(String... authoritys) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null){
			return false;
		}
		Collection<GrantedAuthority> granteds = (Collection<GrantedAuthority>) authentication.getAuthorities();
		if(granteds == null){
			return false;
		}
		for (String a : authoritys) {
			for (GrantedAuthority authority : granteds) {
				if (a.equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}

	public static void saveUserDetailsToContext(UserDetails userDetails,HttpServletRequest request) {
		
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			return context.getAuthentication();
		}
		return null;
	}

	public static PasswordEncoder getPasswordEncoder() {
		DaoAuthenticationProvider daoProvider = (DaoAuthenticationProvider)SpringContextUtil.getBean("daoProvider",DaoAuthenticationProvider.class);
		return (PasswordEncoder) ClassUtil.getValue(daoProvider, "passwordEncoder");
	}
}
