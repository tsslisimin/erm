package com.coomia.erm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.dao.ErmFieldValDao;
import com.coomia.erm.entity.ErmFieldValEntity;
import com.coomia.erm.service.ErmFieldValService;



@Service("ermFieldValService")
public class ErmFieldValServiceImpl implements ErmFieldValService {
	@Autowired
	private ErmFieldValDao ermFieldValDao;
	
	@Override
	public ErmFieldValEntity queryObject(Integer id){
		return ermFieldValDao.queryObject(id);
	}
	
	@Override
	public List<ErmFieldValEntity> queryList(Map<String, Object> map){
		return ermFieldValDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ermFieldValDao.queryTotal(map);
	}
	
	@Override
	public void save(ErmFieldValEntity ermFieldVal){
		ermFieldValDao.save(ermFieldVal);
	}
	
	@Override
	public void update(ErmFieldValEntity ermFieldVal){
		ermFieldValDao.update(ermFieldVal);
	}
	
	@Override
	public void delete(Integer id){
		ermFieldValDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ermFieldValDao.deleteBatch(ids);
	}
	
}
