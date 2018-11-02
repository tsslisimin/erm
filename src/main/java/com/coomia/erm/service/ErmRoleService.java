package com.coomia.erm.service;

import com.coomia.erm.entity.ErmRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmRoleService {
	
	ErmRoleEntity queryObject(Integer id);
	
	List<ErmRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmRoleEntity ermRole);
	
	void update(ErmRoleEntity ermRole);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
