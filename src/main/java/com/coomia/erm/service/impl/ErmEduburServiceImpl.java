package com.coomia.erm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coomia.erm.dao.ErmEduburDao;
import com.coomia.erm.entity.ErmEduburEntity;
import com.coomia.erm.service.ErmEduburService;



@Service("ermEduburService")
public class ErmEduburServiceImpl implements ErmEduburService {
	@Autowired
	private ErmEduburDao ermEduburDao;
	
	@Override
	public ErmEduburEntity queryObject(Integer id){
		return ermEduburDao.queryObject(id);
	}
	
	@Override
	public List<ErmEduburEntity> queryList(Map<String, Object> map){
		return ermEduburDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ermEduburDao.queryTotal(map);
	}
	
	@Override
	public void save(ErmEduburEntity ermEdubur){
		ermEdubur.setCreateTime(new Date());
		ermEduburDao.save(ermEdubur);
	}
	
	@Override
	public void update(ErmEduburEntity ermEdubur){
		ermEdubur.setUpdateTime(new Date());
		ermEduburDao.update(ermEdubur);
	}
	
	@Override
	public void delete(Integer id){
		ermEduburDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ermEduburDao.deleteBatch(ids);
	}
	
}
