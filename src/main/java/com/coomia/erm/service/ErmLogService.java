package com.coomia.erm.service;

import com.coomia.erm.entity.ErmApplyEntity;
import com.coomia.erm.entity.ErmFamilyEntity;
import com.coomia.erm.entity.TbErmLog;

import java.util.List;
import java.util.Map;

/**
 * 家庭信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmLogService {
	
	TbErmLog queryObject(Long id);
	
	List<TbErmLog> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TbErmLog tbErmLog);
	
	void update(TbErmLog tbErmLog);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
