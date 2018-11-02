package com.coomia.erm.service;

import com.coomia.erm.entity.ErmFieldEntity;

import java.util.List;
import java.util.Map;

/**
 * 指标定义
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:53
 */
public interface ErmFieldService {
	
	ErmFieldEntity queryObject(Integer id);
	
	List<ErmFieldEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmFieldEntity ermField);
	
	void update(ErmFieldEntity ermField);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
