package com.coomia.erm.service;

import com.coomia.erm.entity.ErmReportSubEntity;

import java.util.List;
import java.util.Map;

/**
 * 报表订阅
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-17 00:14:21
 */
public interface ErmReportSubService {
	
	ErmReportSubEntity queryObject(Integer id);
	
	List<ErmReportSubEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmReportSubEntity ermReportSub);
	
	void update(ErmReportSubEntity ermReportSub);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
