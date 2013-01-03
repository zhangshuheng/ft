package org.wl.app.demo.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wl.core.security.domain.WlResource;
import org.wl.core.security.domain.WlResourceExample;
import org.wl.core.security.domain.WlUser;
import org.wl.core.security.domain.WlUserExample;
import org.wl.core.security.service.WlResourceService;
import org.wl.core.security.service.WlUserService;
import org.wl.core.spring.SpringContextUtil;
import org.wl.core.spring.mvc.RequestMappingDesc;
import org.wl.core.util.reflect.ClassUtil;

@Controller
@RequestMapping("/demo")
@RequestMappingDesc(name="示例-")
public class DemoController {
	
	@RequestMapping("/showgrid")
	@RequestMappingDesc(name="用户列表")
	public ModelAndView showGrid(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	
	@Autowired
	WlResourceService resourceService;
	
	@RequestMapping("/scan")
	@RequestMappingDesc(name="自动添加资源到资源表")
	@ResponseBody
	public void scan(){
		Map<String, Object> ctrls = SpringContextUtil.getApplicationContext().getBeansWithAnnotation(Controller.class);
		Method mtd = null;
		String rootPath = null, subPath = null,path = null,name=null,rootName=null;
		Map<String,String>urlMap = new HashMap<String,String>();
		
		WlResourceExample example = new WlResourceExample();
		
		
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
							resource.setCode(path);
							resource.setMemo(name);
							resource.setPriority(1);
							resource.setType(1);
							
							//检查是否已经存在
							example.clear();
							example.createCriteria().andCodeEqualTo(path);
							if(resourceService.countByExample(example)==0){
								resourceService.insertSelective(resource);
								name = null;
							}
						}
					}
				}
				rootName = null;
			}
		}
	}
	
	
	@Autowired
	WlUserService userservice ;
	
	@RequestMapping("/listuser")
	@ResponseBody
	@RequestMappingDesc(name="xhr获取用户数据")
	public List<WlUser> listUser(HttpServletRequest request,String start,Integer id,HttpServletResponse rsp){
		if(request.getMethod().equalsIgnoreCase("delete")){
			userservice.deleteByPrimaryKey(id);
		}else{
			rsp.addHeader("Content-Range:", "bytes 0-20/120");
			WlUserExample example = new WlUserExample();
			return userservice.selectByExample(example);
		}
		return null;
	}
	
	@RequestMapping("/loaduser")
	@ResponseBody
	@RequestMappingDesc(name="xhr获取单个用户数据")
	public WlUser loadUser(Integer userid){
		return userservice.selectByPrimaryKey(userid);
	}
	
	@RequestMapping("/saveuser")
	@ResponseBody
	@RequestMappingDesc(name="xhr保存单个用户数据")
	public int saveUser(WlUser record){
		return userservice.insertSelective(record);
	}
	
	@RequestMapping("/cbtree")
	@RequestMappingDesc(name="checkbox tree")
	public ModelAndView cbtree(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	
	
	
	@RequestMapping("/treejson")
	@ResponseBody
	@RequestMappingDesc(name="checkbox tree data")
	public String treejson(){
//		Map<String,Object> treemap = new HashMap<String,Object>();
//		treemap.put("identifier", "name");
//		treemap.put("label", "name");
//		List<HashMap<String, Object>> items = new ArrayList<HashMap<String,Object>>();
//		
//		HashMap<String, Object> item = new HashMap<String, Object>();
//		item.put("name", "John");
//		item.put("type", "parent"); 
//		item.put("checked", true); 
//		List<HashMap<String, Object>> children = new ArrayList<HashMap<String,Object>>();
//		HashMap<String, Object> child = new HashMap<String, Object>();
//		child.put("_reference", "Chuck");
//		children.add(child);
//		child.put("_reference", "Melissa");
//		children.add(child);
//		child.put("_reference", "Nancy");
//		children.add(child);
//		child.put("_reference", "Peter");
//		children.add(child);
//		item.put("children", children);
//		items.add(item);
		
		
		return "{ identifier: \'name\',\n  label: \'name\',\n  items: [\n     { name:\'John\', type:\'parent\',\n        " +
				" children:[{_reference:\'Chuck\'}, {_reference:\'Melissa\'}, " +
				"{_reference:\'Nancy\'}, {_reference:\'Peter\'}] },\n    " +
				" { name:\'Mary\', type:\'parent\', checked: true,\n       " +
				"  children:[{_reference:\'Chuck\'}," +
				" {_reference:\'Melissa\'}, {_reference:\'Nancy\'}, " +
				"{_reference:\'Joan\'}] },\n    " +
				" { name:\'Chuck\', type:\'child\', checked: true },\n   " +
				"  { name:\'Melissa\', type:\'child\', checked: false },\n    " +
				" { name:\'Nancy\', type:\'child\', checked: true },\n     " +
				"{ name:\'Peter\', type:\'parent\', checked: false,\n       " +
				"  children:[{_reference:\'Chantal\'}, {_reference:\'Pascal\'}, " +
				"{_reference:\'Rutger\'}, {_reference:\'Charlotte\'}] },\n    " +
				" { name:\'Joan\', type:\'parent\', checked: true,\n       " +
				"  children:[{_reference:\'Chantal\'}, {_reference:\'Pascal\'}, " +
				"{_reference:\'Rutger\'}, {_reference:\'Charlotte\'}] },\n    " +
				" { name:\'Chantal\', type:\'child\', checked: false,\n        " +
				" children:[{_reference:\'Paul\'}, {_reference:\'Donna\'}, {_reference:\'Eric\'}] },\n" +
				"     { name:\'Rutger\', type:\'child\', checked: false },\n     " +
				"{ name:\'Pascal\', type:\'child\', checked: true },\n   " +
				"  { name:\'Charlotte\', type:\'child\', checked: false },\n  " +
				"   { name:\'Paul\', type:\'child\', checked: true },\n   " +
				"  { name:\'Donna\', type:\'child\', checked: false },\n    " +
				" { name:\'Eric\', type:\'child\', checked: true }\n]}\n";
	}
}
