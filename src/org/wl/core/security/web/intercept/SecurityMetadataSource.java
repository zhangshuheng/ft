package org.wl.core.security.web.intercept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.wl.core.dao.cache.EhcacheCache;
import org.wl.core.security.domain.WlResource;
import org.wl.core.security.domain.WlResourceExample;
import org.wl.core.security.service.WlResourceService;

public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {
	private Lock lock = new ReentrantLock();

	@Autowired
	private WlResourceService resourceService;

	private EhcacheCache ehcache;
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	public static final String LOAD_URL_AUTHORITIES_CACHE_KEY = "LOAD_URL_AUTHORITIES_CACHE_KEY";

	public void afterPropertiesSet() throws Exception {
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfigAttribute> getAttributes(Object filter) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) filter).getRequestUrl();
		Object cache = this.ehcache.getObject("LOAD_URL_AUTHORITIES_CACHE_KEY");
		if(cache == null) {
			loadResourceDefine();
		}
		resourceMap = (Map<String, Collection<ConfigAttribute>>) this.ehcache.getObject("LOAD_URL_AUTHORITIES_CACHE_KEY");
		return resourceMap.get(requestUrl);
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
	//加载所有资源与权限的关系
	private void loadResourceDefine() {
		Object cache = this.ehcache.getObject("LOAD_URL_AUTHORITIES_CACHE_KEY");
		if(cache==null) {
			try{
				this.lock.lock();
				resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
				WlResourceExample example = new WlResourceExample();
				List<WlResource> resources = this.resourceService.selectByExample(example);
				if(resources == null)resources = new ArrayList<WlResource>();
				for (WlResource resource : resources) {
					Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
					//以权限名封装为Spring的security Object
					ConfigAttribute configAttribute = new SecurityConfig(resource.getName());
					configAttributes.add(configAttribute);
					resourceMap.put(resource.getUrl(), configAttributes);
				}
				this.ehcache.putObject("LOAD_URL_AUTHORITIES_CACHE_KEY", resourceMap);
			} finally {
				this.lock.unlock();
		    }
		}
	}

	
	public void setEhcache(EhcacheCache ehcache) {
		this.ehcache = ehcache;
	}
}