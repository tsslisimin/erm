package com.coomia.erm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coomia.erm.entity.ErmFieldValEntity;

/**
 * 指标值
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:53
 */
@Mapper
public interface ErmFieldValDao extends BaseDao<ErmFieldValEntity> {
	
	/**
	 * 根据key查找value
	 * @param fieldValKey
	 * @return
	 */
	ErmFieldValEntity queryObjectBykey(String fieldValKey);
	/**
	 * 根据fieldId和下拉值获取下拉key
	 * @param fieldId
	 * @param val
	 * @return
	 */
	ErmFieldValEntity queryObjectByFieldIdAndVal(@Param("fieldId")Integer fieldId,@Param("val") String val);
	
	/**
	 *根据field查询其field val list
	 * queryValListByField:(这里用一句话描述这个方法的作用). 
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * TODO(这里描述这个方法的执行流程 – 可选).
	 * TODO(这里描述这个方法的使用方法 – 可选).
	 * TODO(这里描述这个方法的注意事项 – 可选).
	 *
	 * @author spancer
	 * @param fieldId
	 * @return
	 * @since JDK 1.6
	 */
	List<ErmFieldValEntity> queryValListByField(@Param("fieldId")Integer fieldId, @Param("flag") Integer flag);
	
	/**
	 * SELECT field_id, MAX(val_weight) FROM `tb_erm_field_val` WHERE flag = 0 GROUP BY field_id; 
	 * queryDefaultScore:(这里用一句话描述这个方法的作用). 
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
	List<Map<String, Object>> queryDefaultScore(@Param("flag") Integer flag);
	
}
