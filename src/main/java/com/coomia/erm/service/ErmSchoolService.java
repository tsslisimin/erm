package com.coomia.erm.service;

import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.util.Query;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 学校信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmSchoolService {

  ErmSchoolEntity queryObject(Integer id);

  List<ErmSchoolEntity> queryList(Map<String, Object> map);

  int queryTotal(Map<String, Object> map);

  boolean save(ErmSchoolEntity ermSchool);

  void update(ErmSchoolEntity ermSchool);

  void delete(Integer id);

  void deleteBatch(Integer[] ids);

  public Map<String, Object> importSchoolInfo(InputStream input, Integer ebId);

  /**
   * 通过项目id查询学校
   * 
   * @param query
   * @return
   */
  List<ErmSchoolEntity> queryListByFundedId(Query query);
  /**
   * 
   * queryObjectBySchCode:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param schCode
   * @return
   * @since JDK 1.6
   */
  ErmSchoolEntity queryObjectBySchCode(String schCode);
  
  /**
   * 根据fundid查询这个项目都默认有哪些学校是与之对应的。
   * queryInitSchoolsByFundId:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param fundId
   * @return
   * @since JDK 1.6
   */
  List<ErmSchoolEntity> queryInitSchoolsByFundId(Integer fundId);
  /**
   * 查询学校所有班级
   * @param params
   * @return
   */
  List<Map<String, Object>> querySchoolClazzs(Map<String, Object> params);
  /**
   * 查询学校的所有年级班级List
   * @param params
   * @return
   */
  Map<String, Set<String>> querySchGradeClazzList(Map<String, Object> params);
}
