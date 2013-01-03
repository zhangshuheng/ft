package org.wl.core.security.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.wl.core.security.domain.WlUser;
import org.wl.core.security.service.WlResourceService;
import org.wl.core.security.service.WlRoleService;
import org.wl.core.spring.SpringContextUtil;

public class SimpleUser implements UserDetails{

	private static final long serialVersionUID = 1031301459059227881L;
	
	private final static Log log = LogFactory.getLog(SimpleUser.class);
	
	private WlUser user;
	private Map<String,Object> data;
	
	public SimpleUser(WlUser user) {
		super();
		this.user = user;
		this.data = new HashMap<String, Object>();
		SpringContextUtil.autowireBean(this);
	}

	@Autowired
	WlRoleService roleService;
	
	@Autowired
	WlResourceService resourceService;
	
	/**
	 * 获取用户权限
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		log.debug("获取用户角色权限");
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("userid", user.getUserid());
//		Set<WlRole> roles = roleService.getUserRoles(map);
//		if(roles != null){
//			for(WlRole role : roles) {
//				map.clear();
//				map.put("roleid", role.getRoleid());
//				Set<WlResource> tempRes = resourceService.getRoleResources(map);
//				for(WlResource res : tempRes) {
//					grantedAuthorities.add(new SimpleGrantedAuthority(res.getName()));
//				}
//			}
//		}
		
		
//		if(ObjectUtil.isNull(user.getUserGroups()))return new ArrayList<GrantedAuthority>();
//		for (UserGroup userGroup : this.user.getUserGroups()) {
//			if (!userGroup.isEnabled())
//				continue;
//			ObjectUtil.add(grantedAuthorities, userGroup.getUrlAuthorities(),"authority");
//			ObjectUtil.add(grantedAuthorities, userGroup.getRoleAuthorities(),"authority");
//			ObjectUtil.add(grantedAuthorities, userGroup.getGroupAuthorities(),"authority");
//			ObjectUtil.add(grantedAuthorities, userGroup.getMenuAuthorities(),"authority");
//		}
		return grantedAuthorities;
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getAccount();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public WlUser getUser() {
		return user;
	}

	public void data(String key, Object value) {
		data.put(key, value);
	}

	public Object data(String key) {
		return data.get(key);
	}
	
}
