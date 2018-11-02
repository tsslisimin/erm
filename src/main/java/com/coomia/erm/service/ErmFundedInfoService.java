package com.coomia.erm.service;

import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmFundedInfoEntity;
import com.coomia.erm.util.Query;

/**
 * 学生资助信息表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmFundedInfoService {
	
	ErmFundedInfoEntity queryObject(Integer id);
	
	List<ErmFundedInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmFundedInfoEntity ermFundedInfo);
	
	void update(ErmFundedInfoEntity ermFundedInfo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	/**
	 * 查询审批信息Map集合
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> queryMapList(Query query);
	/**
	 * 查询总数
	 * @param query
	 * @return
	 */
	int queryMapListTotal(Query query);
	
	
	/**
	 * 按student fund id 查
	 * queryFundCountByStuFund  TODO
	 * @param map
	 * @return
	 */
	int queryAuditSuccCountByStuFund(int stuFundId, int status);
	
	/**
	 * 按 fund id 查
	 * queryFundCount  TODO
	 * @param schoolId
	 * @param fundId
	 * @param status
	 * @return
	 */
	int queryFundCount(int schoolId, int fundId, int status);
	
	/**
	 * 根据 stuFund & log表中的status 查询还剩下多少可提交的名额
	 * queryRemainCountByStuFund  TODO
	 * @param stuFundId
	 * @param status
	 * @param schFundId TODO
	 * @return
	 */
	int queryRemainCountByStuFund(int stuFundId, int status, int schId, int schFundId);

  /**
   * queryStuFundBySchFund TODO
   * 
   * @param schFundId
   * @param stuId
   * @return
   */
  ErmFundedInfoEntity queryStuFundBySchFund(Integer schFundId, Integer stuId);
  /**
   * 信息查询
   * @param query
 * @param dict 
   * @return
   */
  List<Map<String, Object>> queryFundedInfoMapList(Query query, Map<String, Map<Integer, String>> dict);
  /**
   * 获取信息查询总数
   * @param query
   * @return
   */
  int queryFundedInfoMapListTotal(Query query);
  /**
   * 统计报表-按资助学校统计
   * @param query
 * @param dict 
   * @return
   */
  List<Map<String, Object>> queryfundedSchoolReport(Query query, Map<String, Map<Integer, String>> dict);
  /**
   * 统计报表-按资助学校统计总条数
   * @param query
   * @return
   */
  int queryfundedSchoolReportTotal(Query query);
  /**
   * 统计报表-按资助类型统计
   * @param query
   * @param dict
   * @return
   */
  List<Map<String, Object>> queryfundedTypeReport(Query query, Map<String, Map<Integer, String>> dict);
  /**
   * 统计报表-按资助学校统计总条数
   * @param query
   * @return
   */
  int queryfundedTypeReportTotal(Query query);
  /**
   * 获取echart显示的数据
   * @param ermFunedInfoListMap
   * @param string
   * @param string2
   * @return
   */
  Map<String,Object> getEchartsDataMap(List<Map<String, Object>> ermFunedInfoListMap, String xKey, String yKey);
  /**
   * 获取echart饼图数据
   * @param ermFunedInfoListMap
   * @param string
   * @param string2  
   * @return
   */
  List<Map<String,Object>> getEchartsPieDataListMap(List<Map<String, Object>> ermFunedInfoListMap, String xKey, String yKey);
  /**
   * 获取导出报表数据
   * @param query
   * @param reportType 
   * @return
   */
  List<Map<String, Object>> queryReportExportListMap(Query query, String reportType);

  /**
   * queryAppliedStudentsCount  TODO
   * @param schoolId
   * @param year
   * @return
   */
  int queryAppliedStudentsCount(Integer schoolId, int year);

  /**
   * queryFundSuccessStudentCount  TODO
   * @param schoolId
   * @param year
   * @return
   */
  int queryFundSuccessStudentCount(Integer schoolId, int year);
  
  /**
   * 查询有多少待审核名单 
   * queryToAuditUserCount  TODO
   * @param year
   * @param status
   * @param schId
   * @param fundId
   * @return
   */
  int queryToAuditUserCount(int year, int status, Integer schId, Integer fundId);
  /**
   * 获取所有报表的年份
   * @param params
   * @return
   */
  List<Integer> getReportYears(Map<String, Object> params);

  /**
   * queryRemainCountBySchFund  TODO
   * @param schFundId
   * @return
   */
  int queryRemainCountBySchFund(int schFundId, int status);
  /**
   * 获取教育局的资助概览
   * @param ebId
   * @return
   */
  Map<String, Object> queryEBViewData(Integer ebId);
  
  /**
   * 查询这个状态下的学校项目还有多少没走完流程
   * queryRemainUndeltCount  TODO
   * @param schFundId
   * @param status
   * @return
   */
  int queryRemainUndeltCount(int schFundId, int status);
  /**
   * 获取动态列下载信息
   * @param cloumnFields
   * @param reportListMap
   * @return
   */
  Map<String, Object> getDynamicInfo(String cloumnFields, List<Map<String, Object>> reportListMap);
  /**
   * 查询学生资助信息
   * @param params
   * @param dict
   * @return
   */
  List<Map<String, Object>> queryStudentFundinfoList(Map<String, Object> params, Map<String, Map<Integer, String>> dict);

  int queryStudentFundinfoListTotal(Map<String, Object> params);

  List<ColumnHeader> querydiffStudentHeader(List<ColumnHeader> tableHeader, String schFundedId);
  /**
   * 获取学生自定义指标信息
   * @param params
   * @return
   */
  List<Map<String, Object>> fundedStudentById(Map<String, Object> params);
  
  ErmFundedInfoEntity queryObject(Integer schFundId, String idcard, Integer schId);

  List<Map<String, Object>> fundedStudentImgById(Map<String, Object> params);
}
