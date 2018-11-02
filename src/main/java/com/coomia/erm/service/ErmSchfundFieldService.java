package com.coomia.erm.service;

import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ColumnHeader;

/**
 * 学校指标
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:52
 */
public interface ErmSchfundFieldService {
	
	/**
	 * 获取学校指标下载模板表头
	 * @param params
	 * @return
	 */
	List<ColumnHeader> querySchoolFieldHeader(Map<String, Object> params);
	/**
	 * 获取学校指标下载模板表头,如果有需要查码表数据的，加上码表数据
	 * @param params
	 * @return
	 */
	List<ColumnHeader> querySchoolFieldHeader(Map<String, Object> params,List<Map<String,Object>> dicts);
}
