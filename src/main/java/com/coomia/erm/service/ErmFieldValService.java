package com.coomia.erm.service;

import com.coomia.erm.entity.ErmFieldValEntity;

import java.util.List;
import java.util.Map;

/**
 * 指标值
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:53
 */
public interface ErmFieldValService {
	
	ErmFieldValEntity queryObject(Integer id);
	
	List<ErmFieldValEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmFieldValEntity ermFieldVal);
	
	void update(ErmFieldValEntity ermFieldVal);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
