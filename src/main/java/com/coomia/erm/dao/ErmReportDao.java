package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmReportEntity;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 报表信息表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-17 00:14:22
 */
@Mapper
public interface ErmReportDao extends BaseDao<ErmReportEntity> {

  /**
   * queryFundGroupbyType  TODO
   * @param p
   * @return
   */
  Map<String, Integer> queryFundGroupbyType(Map<String, Object> p);

  /**
   * queryFundGroupbySchool  TODO
   * @param p
   * @return
   */
  Map<String, Integer> queryFundGroupbySchool(Map<String, Object> p);
	
}
