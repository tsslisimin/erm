package com.coomia.erm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.dao.ErmStuValueDao;
import com.coomia.erm.entity.ErmStuValueEntity;
import com.coomia.erm.service.ErmStuValueService;



@Service("ermStuValueService")
public class ErmStuValueServiceImpl implements ErmStuValueService {
	@Autowired
	private ErmStuValueDao ermStuValueDao;
	
	@Override
	public ErmStuValueEntity queryObject(Integer id){
		return ermStuValueDao.queryObject(id);
	}
	
	@Override
	public List<ErmStuValueEntity> queryList(Map<String, Object> map){
		return ermStuValueDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ermStuValueDao.queryTotal(map);
	}
	
	@Override
	public void save(ErmStuValueEntity ermStuValue){
		ermStuValueDao.save(ermStuValue);
	}
	
	@Override
	public void update(ErmStuValueEntity ermStuValue){
		ermStuValueDao.update(ermStuValue);
	}
	
	@Override
	public void delete(Integer id){
		ermStuValueDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ermStuValueDao.deleteBatch(ids);
	}
	
}
