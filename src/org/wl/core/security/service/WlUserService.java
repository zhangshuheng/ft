package org.wl.core.security.service;

import java.util.List;
import java.util.Map;

import org.wl.core.security.domain.WlUser;

/**
 * sys_user Service
 * 
 * @author alpha zhang
 */
public interface WlUserService {

	
	/**
	 * 查询	sys_user  的所有数据
	 * @param Map<String, String> map
	 * @return java.util.List<org.fairytale.biz.system.security.domain.WlUser>
	 */
	public List<WlUser> getWlUsers(Map<String, Object> map);
	
	/**
	 * 添加	sys_user  
	 * @param WlUser WlUser
	 */
	public void createWlUser( WlUser WlUser);
	
	
	/**
	 * 删除	sys_user  
	 * @param WlUser WlUser
	 */
	public void deleteWlUser( WlUser WlUser);
	
	
	/**
	 * 修改	sys_user  
	 * @param WlUser WlUser,WlUser oldWlUser
	 */
	public void updateWlUser( WlUser WlUser,WlUser oldWlUser);
	
}
