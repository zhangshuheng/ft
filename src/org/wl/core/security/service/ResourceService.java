package org.wl.core.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wl.core.security.domain.Resource;

public interface ResourceService {
	public List<Resource> getResources(Map<String,String> map);

	public Set<Resource> getRoleResources(Map<String, Object> map);
}
