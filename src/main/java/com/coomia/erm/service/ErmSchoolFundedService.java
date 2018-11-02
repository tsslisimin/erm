package com.coomia.erm.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ErmSchFundExt;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.util.Query;

/**
 * 项目资助
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
public interface ErmSchoolFundedService {
	
	ErmSchoolFundedEntity queryObject(Integer id);
	
	List<ErmSchoolFundedEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmSchoolFundedEntity ermSchoolFunded);
	
	void update(ErmSchoolFundedEntity ermSchoolFunded);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	  /**
     *  学生项目资助ID 查询学校的项目资助信息
     * queryObjectBy  TODO
     * @param stuFundId
     * @return
     */
    ErmSchoolFundedEntity queryObjectByStuFundId(Integer stuFundId);
    

    /**
     * queryObjectBySchFundId  TODO
     * @param schFundid
     * @param schid
     * @return
     */
    ErmSchoolFundedEntity queryObjectByFundId(Integer schFundid, Integer schid);
    /**
     * 导入分配资助名额信息
     * @param inputStream
     * @param ebId 
     * @param fundedId 
     * @return
     */
	Map<String, Object> importSchoolFundedInfo(InputStream inputStream, Integer ebId, Integer fundedId);
	/**
	 * 创建下载模板
	 * @param fileName
	 * @param params
	 */
	void downLoadSchoolFundedTemplate(String fileName, Map<String, Object> params);
	
	/**
	 * 根据fundid type  查询学校项目列表
	 * queryList  TODO
	 * @param map
	 * @return
	 */
	List<ErmSchoolFundedEntity> querySchFundList(Map<String, Object> map);
	
	
	int querySchFundListTotal(Map<String, Object> map);
	/**
	 * 批量设置国家资助
	 * @param ermSchoolFunded
	 * @return
	 */
  Map<String, Object> batchUpdateFunded(ErmSchoolFundedEntity ermSchoolFunded);

  ErmSchoolFundedEntity queryObjectByFundId(Integer id, Integer schoolId, Integer year,
      String semester);
  
  ErmSchoolFundedEntity queryObjectByFundId(Integer fundId, Integer schid, Integer year,
      String semester, String schZone, String level);

  List<ErmSchoolFundedEntity> querySchFundSimpleList(Query query);
  
  /**
   * 添加或修改
   * saveOrUpdateSchFundExt:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @return
   * @since JDK 1.6
   */
  boolean saveOrUpdateSchFundExt(ErmSchFundExt ext);
  
  
  /**
   * 
   * doChangeSchFundStatud:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param schFundId
   * @param targetStatus
   * @return
   * @since JDK 1.6
   */
  boolean doChangeSchFundStatud(int schFundId, FundStatus targetStatus);
    
}
