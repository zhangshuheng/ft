package org.wl.app.common.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wl.core.spring.SpringContextUtil;
import org.wl.core.util.reflect.ClassUtil;

@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		List<String> urls = new ArrayList<String>();
		Map<String, Object> ctrls = SpringContextUtil.getApplicationContext().getBeansWithAnnotation(Controller.class);
		Method mtd = null;
		String rootPath = null, subPath = null,path = null;
		for (Iterator<Map.Entry<String, Object>> iter = ctrls.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, Object> entry = iter.next();
			Class<?> clazz = (Class<?>) entry.getValue().getClass();
			RequestMapping mapping = ClassUtil.getClassGenricType(clazz, RequestMapping.class);
			
			if (mapping.value().length > 0) {
				rootPath = mapping.value()[0];
				Method[] mtds = clazz.getMethods();
				if (mtds != null) {
					for (int i = 0; i < mtds.length; i++) {
						mtd = mtds[i];
						RequestMapping methodmapping = mtd.getAnnotation(RequestMapping.class);
						if (methodmapping != null && methodmapping.value().length > 0) {
							subPath = methodmapping.value()[0];
							path = rootPath +"/"+ subPath + ".do";
							path = path.replaceAll("//", "/");
							path = path.replaceAll("//", "/");
							urls.add(path);
						}
					}
				}
			}
		}
		mv.addObject("urls",urls);
		return mv;
	}
}
