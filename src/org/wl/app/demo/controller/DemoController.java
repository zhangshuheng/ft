package org.wl.app.demo.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wl.core.security.domain.WlUser;
import org.wl.core.security.service.WlUserService;

@Controller
@RequestMapping("/demo")
public class DemoController {
	
	@RequestMapping("/showgrid")
	public ModelAndView showGrid(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	
	@Autowired
	WlUserService userservice ;
	
	@RequestMapping("/listuser")
	@ResponseBody
	public List<WlUser> listUser(String start,HttpServletResponse rsp){
		rsp.addHeader("Content-Range:", "bytes 0-20/120");
		return userservice.getWlUsers(new HashMap<String,Object>());
	}
	
	@RequestMapping("/loaduser")
	@ResponseBody
	public WlUser loadUser(Integer userid){
		return userservice.selectByPrimaryKey(userid);
	}
	
	@RequestMapping("/saveuser")
	@ResponseBody
	public int saveUser(WlUser record){
		return userservice.insertSelective(record);
	}
}
