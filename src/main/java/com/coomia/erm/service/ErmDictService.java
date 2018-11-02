package com.coomia.erm.service;

import com.coomia.erm.entity.ErmDictEntity;
import com.coomia.erm.util.Query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 字典表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-08 23:37:37
 */
public interface ErmDictService {
	
	ErmDictEntity queryObject(Integer id);
	
	List<ErmDictEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmDictEntity ermDict);
	
	void update(ErmDictEntity ermDict);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	/**
	 * 
	 * @param query
	 * @return
	 */
	Map<String, Object> querystuDict(Query query);
	/**
	 * 生成学期字典
	 * @param years
	 * @return
	 */
	List<String> createSemester(String years);
	/**
	 * 获取学期字典
	 * @return
	 */
	Map<String,Object> getSemesterMap();
	/**
	 * 根据code获取name
	 * @param semester
	 * @return
	 */
	String getDictNameByCode(String code);

	ErmDictEntity	getDictNameByName(@Param("dictName")String dictName,@Param("type")String type);


	List<ErmDictEntity>   getDictByType(@Param("type")String type);
}
