package com.coomia.erm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.coomia.erm.dao.ErmRoleDao;
import com.coomia.erm.entity.ErmRoleEntity;
import com.coomia.erm.service.ErmRoleService;



@Service("ermRoleService")
public class ErmRoleServiceImpl implements ErmRoleService {
	@Autowired
	private ErmRoleDao ermRoleDao;
	
	@Override
	public ErmRoleEntity queryObject(Integer id){
		return ermRoleDao.queryObject(id);
	}
	
	@Override
	public List<ErmRoleEntity> queryList(Map<String, Object> map){
		return ermRoleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ermRoleDao.queryTotal(map);
	}
	
	@Override
	public void save(ErmRoleEntity ermRole){
		ermRoleDao.save(ermRole);
	}
	
	@Override
	public void update(ErmRoleEntity ermRole){
		ermRoleDao.update(ermRole);
	}
	
	@Override
	public void delete(Integer id){
		ermRoleDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ermRoleDao.deleteBatch(ids);
	}
	
}
