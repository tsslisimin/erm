package com.coomia.erm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coomia.erm.entity.ErmStuValueEntity;

/**
 * 学生指标值
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:52
 */
@Mapper
public interface ErmStuValueDao extends BaseDao<ErmStuValueEntity> {

	/**
	 * SELECT val.stu_id,val.field_id, val.field_val_val,ff.weight, fv.val_weight
	 * FROM tb_erm_stu_value val LEFT JOIN tb_erm_field ff ON val.field_id=ff.id
	 * LEFT JOIN tb_erm_field_val fv ON ff.id=fv.field_id AND
	 * val.field_val_key=fv.val_key WHERE val.stu_id =1 AND val.schfund_id =1
	 * queryStuVals TODO
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryStuVals(Map<String, Object> param);

	/**
	 * 
	 * queryToAuditStudents TODO
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryToAuditStudents(Map<String, Object> param);

	/**
	 * 
	 * queryToAuditStudentsCount TODO
	 * 
	 * @param param
	 * @return
	 */
	int queryToAuditStudentsCount(Map<String, Object> param);

	/**
	 * 根据学生Id和字段id判断关系是否存在
	 * 
	 * @param id
	 * @param fieldId
	 * @return
	 */
	ErmStuValueEntity queryObjectByStuIdAndFieldId(@Param("studentId") Integer studentId,
			@Param("fieldId") Integer fieldId);
	
  ErmStuValueEntity queryObjectByStuValue(@Param("studentId") Integer studentId,
	    @Param("fieldId") Integer fieldId, @Param("valKey") String valKey);
	/**
	 * 根据学生id修改指标状态
	 * @param  
	 * @param i
	 */
	void updateFlagByStuId(@Param("stuId") Integer stuId,@Param("flag")Integer flag );
	/**
	 * 申报对象名单、资助学生名单
	 * @param p3
	 * @return
	 */
	List<Map<String, Object>> queryApply(Map<String, Object> p3);
	/**
	 * 申报对象名单、资助学生名单总和
	 * @param p3
	 * @return
	 */
	int queryApplyCount(Map<String, Object> p3);

}
