package com.coomia.erm.service;

import java.util.List;
import java.util.Map;

import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.util.Query;
import org.apache.ibatis.annotations.Param;

/**
 * 资助信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmFundedService {

  ErmFundedEntity queryObject(Integer id);
  
  ErmFundedEntity queryObjectOriginal(Integer id);

  List<ErmFundedEntity> queryList(Map<String, Object> map);

  int queryTotal(Map<String, Object> map);

  boolean save(ErmFundedEntity ermFunded);

  boolean saveShcFund(ErmFundedEntity ermFunded,UserContext user);

  void update(ErmFundedEntity ermFunded);

  void delete(Integer id);

  void deleteBatch(Integer[] ids);

  /**
   * startFunded 启动项目
   * 
   * @param id
   * @return
   */
  boolean doStartFund(Integer id);

  /**
   * getFundStatus 查询项目状态
   * 
   * @param id
   * @return
   */
  int getFundStatus(Integer id);

  /**
   * 
   * doStopFund 停止项目
   * 
   * @param id
   * @return
   */
  boolean doStopFund(Integer id);

  /**
   * 查询学校资助项目信息
   * 
   * @param map
   * @return
   */
  List<ErmFundedEntity> queryListBySchoolId(Map<String, Object> map);

  /**
   * 查询所有项目的统计报表
   * 
   * @param query
   * @return
   * @author leequn
   * @date 2017年11月16日 下午2:39:04
   */
  List<Map<String, Object>> queryTotalReportMapList(Query query);

  /**
   * 查询所有项目统计报表的条数
   * 
   * @param query
   * @return
   * @author leequn
   * @date 2017年11月16日 下午2:39:29
   */
  int queryTotalReportMapListTotal(Query query);

  /**
   * 获取教育局资助概览
   * 
   * @param query
   * @return
   */
  Map<String, Object> getReportOverview(Query query);

  /**
   * 查询可审批的项目 queryFundList TODO
   * 
   * @param map
   * @return
   */
  List<ErmFundedEntity> queryFundList(Map<String, Object> map);

  int queryFundListTotal(Map<String, Object> map);

  /**
   * 
   * queryObjectByMap:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param map
   * @return
   * @since JDK 1.6
   */
  ErmFundedEntity queryObjectByMap(Map<String, Object> map);
  
  /**
   * 启动国家资助类型的项目时使用。
   * doConfigFund:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param ermFundedEntity
   * @return
   * @since JDK 1.6
   */
  public boolean doConfigFund(ErmFundedEntity ermFundedEntity);
  
  /**
   * 
   * doCheckSchFunds:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param ermFundedEntity
   * @return
   * @since JDK 1.6
   */
  public boolean doCheckSchFunds(ErmFundedEntity ermFundedEntity);
  
  /**
   * 
   * doChangeFundStatud:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param fundId
   * @param targetStatus
   * @return
   * @since JDK 1.6
   */
  boolean doChangeFundStatud(int fundId, FundStatus targetStatus);


  ErmFundedEntity	selectByNameAndSemester(@Param("semester")String semester, @Param("name")String name);
}
