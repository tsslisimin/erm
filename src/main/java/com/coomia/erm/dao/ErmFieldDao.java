package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmFieldEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 指标定义
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:53
 */
@Mapper
public interface ErmFieldDao extends BaseDao<ErmFieldEntity> {
	/**
	 * 查询学生指定学校下的指标字段
	 * 基于目前学校指标的最新列表
	 * @param schFundedId
	 * @return
	 */
	List<ErmFieldEntity> queryStuFieldList(@Param("schId") String schId);
	/**
	 * 查询学生录入的自定义字段内容
	 * @param stuId
	 * @return
	 */
	List<Map<String, Object>> queryStuFields(@Param("stuId")Integer stuId);
	
}
