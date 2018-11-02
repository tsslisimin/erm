package com.coomia.erm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coomia.erm.entity.ErmSchFieldEntity;

/**
 * 
 * @author spancer
 *
 */
@Mapper
public interface ErmSchFieldDao extends BaseDao<ErmSchFieldEntity> {

  /**
   * 查询学校的KPI字段
   * 
   * @param param
   * @return
   */
  List<Map<String, Object>> querySchFields(Map<String, Object> param);

  void activeBatch(Integer[] ids);
  
  int queryCount(Map<String, Object> param);

}
