package com.coomia.erm.service;

import com.coomia.erm.entity.ErmReportEntity;

import java.util.List;
import java.util.Map;

/**
 * 报表信息表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-17 00:14:22
 */
public interface ErmReportService {
	
	ErmReportEntity queryObject(Integer id);
	
	List<ErmReportEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmReportEntity ermReport);
	
	void update(ErmReportEntity ermReport);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	/**
	 * SELECT fund.type, COUNT(info.id) schFundCount FROM tb_erm_funded_info info LEFT JOIN tb_erm_school_funded schoolFund ON info.funded_id=schoolFund.id 
      LEFT JOIN tb_erm_funded fund ON schoolFund.funded_id=fund.id 
      WHERE 1=1 AND schoolFund.year =2017 GROUP BY fund.type
      
            查询教育这一年都资助了多少类型及 学生数
             学前  20人
             初中 20人
	 * queryFundGroupbyType  TODO
	 * @param ebId
	 * @param year
	 * @return
	 */
	Map<String, Integer> queryFundGroupbyType(Integer ebId, Integer year);
	
	/**
	 * 
	 * SELECT schoolFund.school_id, COUNT(info.id) schFundCount FROM tb_erm_funded_info info LEFT JOIN tb_erm_school_funded schoolFund ON info.funded_id=schoolFund.id 
      LEFT JOIN tb_erm_funded fund ON schoolFund.funded_id=fund.id 
      WHERE 1=1 AND schoolFund.year =2017 GROUP BY schoolFund.school_id
      
               学校1  20人
              学校2    30人
            
	 * queryFundGroupbySchool  TODO
	 * @param ebId
	 * @param year
	 * @return
	 */
	Map<String, Integer> queryFundGroupbySchool(Integer ebId, Integer year);
	/**
	 * 获取默认报表列表
	 * @param years
	 * @param reportOverViewList 
	 * @param type 
	 * @return
	 */
	List<ErmReportEntity> queryDefaultOverViewList(List<Integer> years, List<ErmReportEntity> reportOverViewList, String type);
}
