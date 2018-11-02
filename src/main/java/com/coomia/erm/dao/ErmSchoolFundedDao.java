package com.coomia.erm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coomia.erm.entity.ErmSchoolFundedEntity;

/**
 * 项目资助
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
@Mapper
public interface ErmSchoolFundedDao extends BaseDao<ErmSchoolFundedEntity> {

	/**
	 * queryObjectByStuFundId 根据学生项目资助ID查询学校资助信息
	 * 
	 * @param stuFundId
	 * @return
	 */
	ErmSchoolFundedEntity queryObjectByStuFundId(Integer stuFundId);

	/**
	 * 
	 * queryObjectBySchFundId TODO
	 * 
	 * @param param
	 * @return
	 */
	ErmSchoolFundedEntity queryObjectByFundId(Map<String, Object> param);

  /**
   * querySchFundList  TODO
   * @param map
   * @return
   */
  List<ErmSchoolFundedEntity> querySchFundList(Map<String, Object> map);

  /**
   * querySchFundListTotal  TODO
   * @param map
   * @return
   */
  int querySchFundListTotal(Map<String, Object> map);

}
