package com.coomia.erm.service;

import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmFieldEntity;
import com.coomia.erm.entity.ErmSchFieldEntity;

/**
 * 学校指标
 * 
 * @author spancer
 *
 */

public interface ErmSchFieldService {

  ErmSchFieldEntity queryObject(Integer id);

  List<ErmSchFieldEntity> queryList(Map<String, Object> map);

  int queryTotal(Map<String, Object> map);

  void save(ErmSchFieldEntity ermSchfundField);

  /**
   * 
   * update:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param field
   * @since JDK 1.6
   */
  void update(ErmSchFieldEntity field);

  void delete(Integer id);

  void deleteBatch(Integer[] ids);

  /**
   * 获取学校指标下载模板表头
   * 
   * @param params
   * @return
   */
  List<ColumnHeader> querySchoolFieldHeader(Map<String, Object> params);
  
  /**
   * 初始化学校的指标配置。
   * 
   * 目标：向：tb_erm_sch_field表添加数据，将tb_erm_field表的数据
   * 写入sch-field表中。
   * 步骤：
   * 1. 查询出所有的tb_erm_field数据
   * 2. 插入数据到tb_erm_sch_field表中。
   * doInitSchFieldsConfig:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @return
   * @since JDK 1.6
   */
  boolean doInitSchFieldsConfig(int schId);
  
  
  /**
   * 新增一个指标及指标项
   * doAddSchField:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param schId
   * @param field
   * @return
   * @since JDK 1.6
   */
  boolean doAddSchField(int schId, ErmFieldEntity field);
  
  /**
   * 检查是否做了field相关的初始化,如果有就返回true, 否则为false.
   * hasInited:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param schId
   * @return
   * @since JDK 1.6
   */
  boolean hasInited(int schId);

  void activeBatch(Integer[] ids);
}
