package org.wl.core.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wl.core.security.domain.WlResource;

public interface WlResourceService {
	public List<WlResource> getResources(Map<String,String> map);

	public Set<WlResource> getRoleResources(Map<String, Object> map);
}
