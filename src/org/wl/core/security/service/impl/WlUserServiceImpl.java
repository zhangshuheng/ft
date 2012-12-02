package org.wl.core.security.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wl.core.security.dao.WlUserMapper;
import org.wl.core.security.domain.WlUser;
import org.wl.core.security.domain.WlUserExample;
import org.wl.core.security.service.WlUserService;
@Service
public class WlUserServiceImpl implements WlUserService {

	@Autowired
	WlUserMapper userMapper;
	@Override
	public List<WlUser> getWlUsers(Map<String, Object> map) {
		// TODO Auto-generated method stub
		WlUserExample example = new WlUserExample();
		example.createCriteria().andNameNotEqualTo(map.get("name").toString());
		userMapper.selectByExample(example);
		return null;
	}

	@Override
	public void createWlUser(WlUser WlUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteWlUser(WlUser WlUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateWlUser(WlUser WlUser, WlUser oldWlUser) {
		// TODO Auto-generated method stub

	}

}
