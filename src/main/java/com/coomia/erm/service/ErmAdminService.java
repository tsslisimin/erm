package com.coomia.erm.service;

import com.coomia.erm.entity.ErmAdminEntity;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.util.Query;

import java.util.List;
import java.util.Map;

/**
 * 管理员信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
public interface ErmAdminService {
	
	ErmAdminEntity queryObject(Integer id);
	
	List<ErmAdminEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmAdminEntity ermAdmin);
	
	void update(ErmAdminEntity ermAdmin);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	/**
	 * 生成学校账号列表
	 * @param query
	 * @return
	 */
	List<ErmAdminEntity> createSchoolAdmin(Query query);

	List<ErmAdminEntity> createSchoolAccount(Query query);


	void createOneSchoolAdmin(ErmSchoolEntity ermSchoolEntity);
}
