package org.wl.app.common.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wl.core.security.domain.WlResource;
import org.wl.core.spring.SpringContextUtil;
import org.wl.core.spring.mvc.RequestMappingDesc;
import org.wl.core.util.reflect.ClassUtil;

@Controller
@RequestMapping("/")
@RequestMappingDesc
public class IndexController {

	@RequestMapping("/index")
	@RequestMappingDesc(name="首页")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		List<WlResource> urls = new ArrayList<WlResource>();
		Map<String, Object> ctrls = SpringContextUtil.getApplicationContext().getBeansWithAnnotation(Controller.class);
		Method mtd = null;
		String rootPath = null, subPath = null,path = null,name=null,rootName=null;
		Map<String,String>urlMap = new HashMap<String,String>();
		for (Iterator<Map.Entry<String, Object>> iter = ctrls.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, Object> entry = iter.next();
			Class<?> clazz = (Class<?>) entry.getValue().getClass();
			RequestMapping mapping = ClassUtil.getClassGenricType(clazz, RequestMapping.class);
			RequestMappingDesc mappingdesc = ClassUtil.getClassGenricType(clazz, RequestMappingDesc.class);
			
			if (mapping.value().length > 0) {
				rootPath = mapping.value()[0];
				if(mappingdesc != null && mappingdesc.name().length()>0){
					rootName =  mappingdesc.name();
				}
				Method[] mtds = clazz.getMethods();
				if (mtds != null) {
					for (int i = 0; i < mtds.length; i++) {
						mtd = mtds[i];
						RequestMapping methodmapping = mtd.getAnnotation(RequestMapping.class);
						RequestMappingDesc methodmappingdesc = mtd.getAnnotation(RequestMappingDesc.class);
						if (methodmapping != null && methodmapping.value().length > 0) {
							subPath = methodmapping.value()[0];
							
							if(methodmappingdesc != null && methodmappingdesc.name().length()>0){
								name = (rootName==null?"":rootName) + methodmappingdesc.name();
							}
							path = rootPath +"/"+ subPath + ".do";
							path = path.replaceAll("//", "/");
							path = path.replaceAll("//", "/");
							urlMap.put("path", path);
							urlMap.put("name", name);
							WlResource resource = new WlResource();
							resource.setUrl(path);
							if(name == null || name.length()==0){
								resource.setName(path);
							}else{
								resource.setName(name);
							}
							urls.add(resource);
							name = null;
						}
					}
				}
				rootName = null;
			}
		}
		mv.addObject("urls",urls);
		return mv;
	}
}
