package com.coomia.erm.service;

import com.coomia.erm.entity.ErmEduburEntity;

import java.util.List;
import java.util.Map;

/**
 * 教育局信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmEduburService {
	
	ErmEduburEntity queryObject(Integer id);
	
	List<ErmEduburEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmEduburEntity ermEdubur);
	
	void update(ErmEduburEntity ermEdubur);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
