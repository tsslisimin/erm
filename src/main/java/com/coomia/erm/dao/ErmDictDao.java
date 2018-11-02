package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmDictEntity;
import com.coomia.erm.util.Query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-08 23:37:37
 */
@Mapper
public interface ErmDictDao extends BaseDao<ErmDictEntity> {

	List<Map<String, Object>> querystuDict(Query query);

	String getDictNameByCode(@Param("code")String code);

	ErmDictEntity	getDictNameByName(@Param("dictName")String dictName,@Param("type")String type);
	List<ErmDictEntity>   getDictByType(@Param("type")String type);
}
