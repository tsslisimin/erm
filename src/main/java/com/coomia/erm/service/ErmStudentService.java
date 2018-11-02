package com.coomia.erm.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmStudentEntity;
import com.coomia.erm.entity.ErmStudentVo;
import com.coomia.erm.util.Query;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 学生信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmStudentService {

  ErmStudentEntity queryObject(Integer id, String type);

  List<ErmStudentEntity> queryList(Map<String, Object> map);

  int queryTotal(Map<String, Object> map);

  void save(ErmStudentEntity ermStudent, Integer fundedId);

  void update(ErmStudentEntity ermStudent);

  void delete(Integer id);

  void deleteBatch(Integer[] ids);

  /**
   * 导入学生信息
   * 
   * @param inputStream
   * @param schoolId
   * @return
   */
  Map<String, Object> importStudentInfo(InputStream inputStream, Integer schoolId, Integer userId);

  /**
   * 根据学生资助ID查询学生信息 queryStudentByStuFundId TODO
   * 
   * @param stuFundId
   * @return
   */
  ErmStudentEntity queryStudentByStuFundId(int stuFundId);

  /**
   * 导入资助学生信息
   * 
   * @param inputStream
   * @param schoolFieldHeader
   * @param fundedId
   * @param schoolId
   * @return
   */
  Map<String, Object> importAuditStudentInfo(InputStream inputStream,
                                             List<ColumnHeader> schoolFieldHeader, Integer fundedId, Integer schoolId, Integer userId);



  /**
   * 导入资助学生信息
   *
   * @param inputStream
   * @param schoolFieldHeader
   * @param fundedId
   * @param schoolId
   * @return
   */
  List<ErmStudentEntity> importAuditStudentInfo(InputStream inputStream);

  /**
   * 保存学生信息
   * 
   * @param ermStudent
   * @param user
   */
  void save(ErmStudentEntity ermStudent, UserContext user, Integer fundedId);

  /**
   * 通过学校ID， 姓名， 身份证找到该学生
   * 
   * @author spancer
   * @param schId
   * @param name
   * @param idCard
   * @return
   * @since JDK 1.6
   */
  ErmStudentEntity queryStudentByIdCard(int schId, String name, String idCard);

  /**
   * 更新学生信息
   */
  void upgradeStudentInfo();

  /**
   * 获取学生申请信息包括：基本信息、贫困信息、申请表信息、证明URL
   * 
   * @param stuId
 * @param schFundId 
 * @param fundId 
   * @return
   */
  Map<String, Object> queryStudentApplyInfoById(Integer stuId, Integer schFundId, Integer fundId);

  /**
   * 班主任认定
   * 
   * @param stuId
   * @param headTeachName
   * @param isOk
   * @return
   */
  Map<String, Object> headTeachConfirm(Integer stuId, String headTeachName, Integer isOk);

  /**
   * 将该学校的学生(困难学生)的flag置为1, 表示, 该学生的贫困指标需求补充填写完整.
   * doSubscrib:(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选). TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选). TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param schoolId
   * @since JDK 1.6
   */
  void doSubscrib(Integer schoolId);
  /**
   * 删除困难学生信息
   * @param stuId
   */
  void deleteDiffInfo(Integer stuId);
  /**
   * 学生银行卡比对
   * @param inputStream
   * @param schoolId
   * @return
   */
  Map<String, Object> compareIdcard(InputStream inputStream, Integer schoolId);
  /**
   * 导入银行卡信息
   * @param inputStream
   * @param schoolId
   * @return
   */
  Map<String, Object> importStudentBankCard(InputStream inputStream, Integer schoolId);

  List<ErmStudentVo> queryStudentsByParam(Map<String, Object> params);

  List<ErmStudentEntity>  queryStudentsBySchoolId(Map<String, Object> params);


    ErmStudentEntity queryObjectByMap(Map<String, Object> map);

  List<Map<String,Object>> queryStudentsByFundIdAndSchoolId(@Param("fundId") Integer fundId, @Param("schoolId") Integer schoolId);
  List<Map<String,Object>> queryStudentsByFundIdAndSchoolIdAndPage(Map<String, Object> params);
  Integer queryStudentsByFundIdAndSchoolIdTotal(Map<String, Object> params);

  Object downloadItems(Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) throws IOException;

  Object exportStudentList(Query query, HttpServletRequest req, HttpServletResponse resp);


}
