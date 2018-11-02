package com.coomia.erm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.util.Query;
import org.apache.ibatis.annotations.Param;

/**
 * 资助信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@Mapper
public interface ErmFundedDao extends BaseDao<ErmFundedEntity> {
	
	public List<ErmFundedEntity> queryListBySchoolId(Map<String, Object> map);
	/**
	 * 查询报表统计总条数
	 *  
	 * @param query
	 * @return
	 * @author leequn
	 * @date 2017年11月16日 下午2:41:56
	 */
	public List<Map<String, Object>> queryTotalReportMapList(Query query);
	/**
	 * 
	 * 查询统计报表记录数
	 * @param query
	 * @return
	 * @author leequn
	 * @date 2017年11月16日 下午2:42:35
	 */
	public int queryTotalReportMapListTotal(Query query);
	/**
	 * 获取教育局报表概览
	 * @param query
	 * @return
	 */
	public Map<String, Object> getReportOverview(Query query);
  /**
   * queryFundList  TODO
   * @param map
   * @return
   */
  public List<ErmFundedEntity> queryFundList(Map<String, Object> map);
  
  int   queryFundListTotal (Map<String, Object> map);


	ErmFundedEntity	selectByNameAndSemester(@Param("semester")String semester,@Param("name")String name);
}
