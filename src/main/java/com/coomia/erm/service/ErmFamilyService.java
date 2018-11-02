package com.coomia.erm.service;

import com.coomia.erm.entity.ErmApplyEntity;
import com.coomia.erm.entity.ErmFamilyEntity;

import java.util.List;
import java.util.Map;

/**
 * 家庭信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmFamilyService {
	
	ErmFamilyEntity queryObject(Integer id);
	
	List<ErmFamilyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmFamilyEntity ermFamily);
	
	void update(ErmFamilyEntity ermFamily);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	/**
	 * 保存申请表信息
	 * @param apply
	 */
	void saveApplyObj(ErmApplyEntity apply);
}
