package org.wl.core.security.service.impl;

import java.util.List;

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
	public int countByExample(WlUserExample example) {
		return userMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(WlUserExample example) {
		return userMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer userid) {
		return userMapper.deleteByPrimaryKey(userid);
	}

	@Override
	public int insert(WlUser record) {
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(WlUser record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public List<WlUser> selectByExample(WlUserExample example) {
		return userMapper.selectByExample(example);
	}

	@Override
	public WlUser selectByPrimaryKey(Integer userid) {
		return userMapper.selectByPrimaryKey(userid);
	}

	@Override
	public int updateByExampleSelective(WlUser record, WlUserExample example) {
		return userMapper.updateByExampleSelective(record,example);
	}

	@Override
	public int updateByExample(WlUser record, WlUserExample example) {
		return userMapper.updateByExample(record,example);
	}

	@Override
	public int updateByPrimaryKeySelective(WlUser record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WlUser record) {
		return userMapper.updateByPrimaryKey(record);
	}

}
