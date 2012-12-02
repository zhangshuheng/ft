package org.wl.core.security.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.wl.core.security.domain.Resource;
import org.wl.core.security.service.ResourceService;
@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Override
	public List<Resource> getResources(Map<String,String> map){
//		TODO
		return null;
	}

	@Override
	public Set<Resource> getRoleResources(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
}
