package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmAuditLogEntity;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 学生资助审批日志表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@Mapper
public interface ErmAuditLogDao extends BaseDao<ErmAuditLogEntity> {
	
  /**
   * 
   * queryLog  TODO
   * @param schFundId
   * @param status
   * @return 
   */
  int queryLog(Map<String, Object> param);
}
