package org.wl.core.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wl.core.security.domain.WlRole;

/**
 * sys_role Service
 * 
 * @author alpha zhang
 */
public interface WlRoleService {

	
	/**
	 * 查询	sys_role  的所有数据
	 * @param Map<String, String> map
	 * @return java.util.List<org.fairytale.biz.system.security.domain.WlRole>
	 */
	public List<WlRole> getWlRoles(Map<String, String> map);
	
	/**
	 * 添加	sys_role  
	 * @param WlRole WlRole
	 */
	public void createWlRole( WlRole WlRole);
	
	
	/**
	 * 删除	sys_role  
	 * @param WlRole WlRole
	 */
	public void deleteWlRole( WlRole WlRole);
	
	
	/**
	 * 修改	sys_role  
	 * @param WlRole WlRole,WlRole oldWlRole
	 */
	public void updateWlRole( WlRole WlRole,WlRole oldWlRole);

	public Set<WlRole> getUserRoles(Map<String, Object> map);
	
}
