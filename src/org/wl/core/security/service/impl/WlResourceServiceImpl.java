package org.wl.core.security.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wl.core.security.dao.WlResourceMapper;
import org.wl.core.security.domain.WlResource;
import org.wl.core.security.domain.WlResourceExample;
import org.wl.core.security.service.WlResourceService;
@Service
public class WlResourceServiceImpl implements WlResourceService {

	@Autowired
	WlResourceMapper resourceMapper;
	@Override
	public int countByExample(WlResourceExample example) {
		return resourceMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(WlResourceExample example) {
		return resourceMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer resourceid) {
		return resourceMapper.deleteByPrimaryKey(resourceid);
	}

	@Override
	public int insert(WlResource record) {
		return resourceMapper.insert(record);
	}

	@Override
	public int insertSelective(WlResource record) {
		return resourceMapper.insertSelective(record);
	}

	@Override
	public List<WlResource> selectByExampleWithRowbounds(WlResourceExample example, RowBounds rowBounds) {
		return resourceMapper.selectByExampleWithRowbounds(example,rowBounds);
	}

	@Override
	public List<WlResource> selectByExample(WlResourceExample example) {
		return resourceMapper.selectByExample(example);
	}

	@Override
	public WlResource selectByPrimaryKey(Integer resourceid) {
		return resourceMapper.selectByPrimaryKey(resourceid);
	}

	@Override
	public int updateByExampleSelective(WlResource record, WlResourceExample example) {
		return resourceMapper.updateByExampleSelective(record,example);
	}

	@Override
	public int updateByExample(WlResource record, WlResourceExample example) {
		return resourceMapper.updateByExample(record,example);
	}

	@Override
	public int updateByPrimaryKeySelective(WlResource record) {
		return resourceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WlResource record) {
		return resourceMapper.updateByPrimaryKey(record);
	}

	@Override
	public int countByModel(WlResource record) {
		return resourceMapper.countByModel(record);
	}

	@Override
	public List<WlResource> selectByModel(WlResource model) {
		return resourceMapper.selectByModel(model);
	}
}
