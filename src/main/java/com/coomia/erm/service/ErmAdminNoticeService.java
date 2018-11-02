package com.coomia.erm.service;

import com.coomia.erm.entity.ErmAdminNoticeEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户消息订阅表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
public interface ErmAdminNoticeService {
	
	ErmAdminNoticeEntity queryObject(Integer id);
	
	List<ErmAdminNoticeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmAdminNoticeEntity ermAdminNotice);
	
	void update(ErmAdminNoticeEntity ermAdminNotice);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	
}
