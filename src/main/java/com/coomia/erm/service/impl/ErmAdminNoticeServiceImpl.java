package com.coomia.erm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coomia.erm.dao.ErmAdminNoticeDao;
import com.coomia.erm.entity.ErmAdminNoticeEntity;
import com.coomia.erm.service.ErmAdminNoticeService;



@Service("ermAdminNoticeService")
public class ErmAdminNoticeServiceImpl implements ErmAdminNoticeService {
	@Autowired
	private ErmAdminNoticeDao ermAdminNoticeDao;
	
	@Override
	public ErmAdminNoticeEntity queryObject(Integer id){
		return ermAdminNoticeDao.queryObject(id);
	}
	
	@Override
	public List<ErmAdminNoticeEntity> queryList(Map<String, Object> map){
		return ermAdminNoticeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ermAdminNoticeDao.queryTotal(map);
	}
	
	@Override
	public void save(ErmAdminNoticeEntity ermAdminNotice){
		ermAdminNotice.setCreateTime(new Date());
		ermAdminNoticeDao.save(ermAdminNotice);
	}
	
	@Override
	public void update(ErmAdminNoticeEntity ermAdminNotice){
		ermAdminNotice.setUpdateTime(new Date());
		ermAdminNoticeDao.update(ermAdminNotice);
	}
	
	@Override
	public void delete(Integer id){
		ermAdminNoticeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ermAdminNoticeDao.deleteBatch(ids);
	}
	
}
