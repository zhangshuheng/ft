package org.wl.core.util.web.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import org.wl.core.util.web.RequestContext;

public class RequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		if(request.getRequestURI().endsWith(".do")){
			RequestContext.getContext(request, response);
		}
		chain.doFilter(request, response);
	}

}
