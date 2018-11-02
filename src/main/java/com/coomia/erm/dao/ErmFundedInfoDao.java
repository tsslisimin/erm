package com.coomia.erm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coomia.erm.entity.ErmFundedInfoEntity;
import com.coomia.erm.util.Query;

/**
 * 学生资助信息表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@Mapper
public interface ErmFundedInfoDao extends BaseDao<ErmFundedInfoEntity> {
	/**
	 * 查询MapList
	 * 
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> queryMapList(Query query);

	/**
	 * 查询总数
	 * 
	 * @param query
	 * @return
	 */
	int queryMapListTotal(Query query);

	/**
	 * 查询学校各流程状态下的学生人数, 按学校ID， 资助项目ID（ref tb_erm_funded) queryFundCount TODO
	 * 
	 * @param map
	 * 
	 *            stuaudit.status = #{status,jdbcType=INTEGER} AND stufund.school_id
	 *            = #{schoolId,jdbcType=INTEGER} AND schfund.funded_id =
	 *            #{fundId,jdbcType=INTEGER}
	 * @return
	 */
	int queryFundCountByFund(Map<String, Object> map);

	/**
	 * 通过fund_info表的ID 和 audit的status来查询还有多少可以审核的人员 SELECT (SELECT schFund.count
	 * FROM tb_erm_school_funded schFund WHERE id = (SELECT stuFund.funded_id FROM
	 * tb_erm_funded_info stuFund WHERE stuFund.id=#{stuFundId,jdbcType=INTEGER})) -
	 * (SELECT COUNT(1) FROM tb_erm_audit_log auditLog JOIN tb_erm_funded_info
	 * stuFund ON auditLog.funded_id=stuFund.id WHERE
	 * stuFund.id=#{stuFundId,jdbcType=INTEGER} AND
	 * auditLog.status=#{status,jdbcType=INTEGER}) AS remainCount
	 * 
	 * queryRemainCoutByStuFund TODO
	 * 
	 * @param map
	 * @return
	 */
	int queryRemainCoutByStuFund(Map<String, Object> map);

	/**
	 * 
	 * queryAuditSuccCountByStuFund 查询成功审核的人员数量，学生资助项目ID & 状态
	 * 
	 * @param map
	 * @return
	 */
	int queryAuditSuccCountByStuFund(Map<String, Object> map);

	/**
	 * queryStuFundByMap TODO
	 * 
	 * @param param
	 * @return
	 */
	ErmFundedInfoEntity queryStuFundByMap(Map<String, Object> param);
	/**
	 * 查询信息
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> queryFundedInfoMapList(Query query);
	/**
	 * 查询信息总数
	 * @param query
	 * @return
	 */
	int queryFundedInfoMapListTotal(Query query);
	/**
	 * 统计报表-按资助学校统计
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> queryfundedSchoolReport(Query query);
	/**
	 * 统计报表-按资助学校统计统计条数
	 * @param query
	 * @return
	 */
	int queryfundedSchoolReportTotal(Query query);
	/**
	 * 统计报表-按资助类型统计
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> queryfundedTypeReport(Query query);
	/**
	 * 统计报表-按资助类型统计统计条数
	 * @param query
	 * @return
	 */
	int queryfundedTypeReportTotal(Query query);
	
	/**
	 * 查询有多少待审核人数
	 * queryToAuditUserCount  TODO
	 * @param param
	 * @return
	 */
	int queryToAuditUserCount(Map<String, Object> param);
	/**
	 * 获取所有报表的年份
	 * @param params
	 * @return
	 */
	List<Integer> getReportYears(Map<String, Object> params);

  /**
   * queryRemainCoutBySchFund  TODO
   * @param param
   * @return
   */
  Integer queryRemainCoutBySchFund(Map<String, Object> param);
  /**
   * 获取教育局资助概览
   * @param ebId
   * @return
   */
  List<Map<String,Object>> queryEBViewData(Integer ebId);
  
  
  /**
   * 
   * queryRemainUndeltCount  TODO
   * @param param
   * @return
   */
  Integer queryRemainUndeltCount(Map<String, Object> param);
  /**
   * 查询学生基本信息
   * @param query
   * @return
   */
  List<Map<String, Object>> queryBaseStudentMapList(Query query);
  
  int queryBaseStudentMapListTotal(Query query);

  List<Map<String, Object>> queryStudentFundinfoList(Map<String, Object> params);

  int queryStudentFundinfoListTotal(Map<String, Object> params);
  
  /**
   * 
   * queryStuFundInfoByStudentMap:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param param
   * @return
   * @since JDK 1.6
   */
  ErmFundedInfoEntity queryStuFundInfoByStudentMap(Map<String, Object> param);
  
  

}
