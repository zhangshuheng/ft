package org.wl.app.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wl.core.spring.mvc.RequestMappingDesc;
import org.wl.core.util.web.RequestContext;

@Controller
@RequestMapping("/auth")
@RequestMappingDesc(name="认证相关-")
public class AuthController {
	
	@RequestMapping("/login")
	@RequestMappingDesc(name="登录")
	public ModelAndView login(){
		RequestContext.getContext().getHttpResponse().setStatus(401);
		ModelAndView mv = new ModelAndView();
		Exception exception = (Exception) RequestContext.getContext().getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if(exception != null){
			mv.addObject("errorMsg", exception.getMessage());
			RequestContext.getContext().getSession().removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		}
		return mv;
	}
}
