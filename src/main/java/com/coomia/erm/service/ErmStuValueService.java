package com.coomia.erm.service;

import com.coomia.erm.entity.ErmStuValueEntity;

import java.util.List;
import java.util.Map;

/**
 * 学生指标值
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:52
 */
public interface ErmStuValueService {
	
	ErmStuValueEntity queryObject(Integer id);
	
	List<ErmStuValueEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmStuValueEntity ermStuValue);
	
	void update(ErmStuValueEntity ermStuValue);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
}
