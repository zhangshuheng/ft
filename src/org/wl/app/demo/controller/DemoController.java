package org.wl.app.demo.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
	public List<WlUser> listUser(HttpServletRequest request,String start,HttpServletResponse rsp){
		if(request.getMethod().equalsIgnoreCase("delete")){
			rsp.addHeader("Content-Range:", "bytes 0-10/20");
		}else{
			rsp.addHeader("Content-Range:", "bytes 0-20/120");
		}
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
	
	@RequestMapping("/cbtree")
	public ModelAndView cbtree(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	@RequestMapping("/treejson")
	@ResponseBody
	public String treejson(){
		return "{ identifier: \'name\',\n  label: \'name\',\n  items: [\n     { name:\'John\', type:\'parent\',\n         children:[{_reference:\'Chuck\'}, {_reference:\'Melissa\'}, {_reference:\'Nancy\'}, {_reference:\'Peter\'}] },\n     { name:\'Mary\', type:\'parent\', checked: true,\n         children:[{_reference:\'Chuck\'}, {_reference:\'Melissa\'}, {_reference:\'Nancy\'}, {_reference:\'Joan\'}] },\n     { name:\'Chuck\', type:\'child\', checked: true },\n     { name:\'Melissa\', type:\'child\', checked: false },\n     { name:\'Nancy\', type:\'child\', checked: true },\n     { name:\'Peter\', type:\'parent\', checked: false,\n         children:[{_reference:\'Chantal\'}, {_reference:\'Pascal\'}, {_reference:\'Rutger\'}, {_reference:\'Charlotte\'}] },\n     { name:\'Joan\', type:\'parent\', checked: true,\n         children:[{_reference:\'Chantal\'}, {_reference:\'Pascal\'}, {_reference:\'Rutger\'}, {_reference:\'Charlotte\'}] },\n     { name:\'Chantal\', type:\'child\', checked: false,\n         children:[{_reference:\'Paul\'}, {_reference:\'Donna\'}, {_reference:\'Eric\'}] },\n     { name:\'Rutger\', type:\'child\', checked: false },\n     { name:\'Pascal\', type:\'child\', checked: true },\n     { name:\'Charlotte\', type:\'child\', checked: false },\n     { name:\'Paul\', type:\'child\', checked: true },\n     { name:\'Donna\', type:\'child\', checked: false },\n     { name:\'Eric\', type:\'child\', checked: true }\n]}\n";
	}
}
