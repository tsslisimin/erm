package com.coomia.erm.dao;

import java.util.List;
import java.util.Map;

import com.coomia.erm.dto.StudentEnterDTO;
import org.apache.ibatis.annotations.Mapper;

import com.coomia.erm.entity.ErmAdjustParam;
import com.coomia.erm.entity.ErmStudentEntity;
import com.coomia.erm.entity.ErmStudentVo;
import com.coomia.erm.util.Query;
import org.apache.ibatis.annotations.Param;

/**
 * 学生信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-12 17:33:56
 */
@Mapper
public interface ErmStudentDao extends BaseDao<ErmStudentEntity> {
  /**
   * queryStudentByStuFundId TODO
   * 
   * @param stuFundId
   * @return
   */
  ErmStudentEntity queryStudentByStuFundId(int stuFundId);

  /**
   * 通过学生身份证查找学生信息
   * 
   * @param idcard
   * @return
   */
  ErmStudentEntity queryObjectByIdCard(String idcard);

  ErmStudentEntity queryObjectByMap(Map<String, Object> map);

  /**
   * 标识学生的FLAG字段为1， 表示，该困难生的困难等级或得分是需要重新去 采集一些指标信息的（新增的指标信息）
   * doTagStudentAsNeedReview:(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选). TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选). TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param schId
   * @return
   * @since JDK 1.6
   */
  int doTagStudentAsNeedReview(int schId);
  
  /**
   * 批量调整困难等级
   * doAdjustBatch:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param map
   * @return
   * @since JDK 1.6
   */
  int doAdjustBatch(ErmAdjustParam adjustParam);
  /**
   * 获取学校的所有班级
   * @param params
   * @return
   */
  List<ErmStudentEntity> queryStudentGroupByClazz(Map<String, Object> params);

  int queryAvailCount(Query query);

  List<ErmStudentEntity> queryAvailList(Map<String, Object> map);

  List<ErmStudentVo> queryStudentsByParam(Map<String, Object> params);

  List<ErmStudentEntity>  queryStudentsBySchoolId(Map<String, Object> params);

  List<Map<String,Object>> queryStudentsByFundIdAndSchoolId(@Param("fundId")Integer fundId,@Param("schoolId")Integer schoolId);


  List<Map<String,Object>> queryStudentsByFundIdAndSchoolIdAndPage(Map<String, Object> params);
   Integer queryStudentsByFundIdAndSchoolIdTotal(Map<String, Object> params);

  List<StudentEnterDTO> sumStudentEnter();
}
