package com.coomia.erm.service;

import com.coomia.erm.entity.ErmAuditLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 学生资助审批日志表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmAuditLogService {
	
	ErmAuditLogEntity queryObject(Integer id);
	
	List<ErmAuditLogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmAuditLogEntity ermAuditLog);
	
	void update(ErmAuditLogEntity ermAuditLog);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
