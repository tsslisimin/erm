
/**
 * Project Name:erm File Name:ErmSchFieldEntity.java Package Name:com.coomia.erm.entity
 * Date:2018年3月11日下午2:09:19 Copyright (c) 2018, spancer.ray All Rights Reserved.
 *
 */

package com.coomia.erm.entity;

/**
 * 学校的指标 ClassName:ErmSchFieldEntity Function: TODO ADD FUNCTION. Reason: TODO ADD REASON. Date:
 * 2018年3月11日 下午2:09:19
 * 
 * @author spancer
 * @version
 * @since JDK 1.6
 * @see
 */
public class ErmSchFieldEntity {

  private Integer id;
  private Integer schId;
  private Integer fieldId;
  private double weight;
  
  private ErmFieldEntity ermFieldEntity;
  
  // 是否显示，1表示显示， 0表示不显示，用于做逻辑删除用。
  private Integer flag;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSchId() {
    return schId;
  }

  public void setSchId(Integer schId) {
    this.schId = schId;
  }

  public Integer getFieldId() {
    return fieldId;
  }

  public void setFieldId(Integer fieldId) {
    this.fieldId = fieldId;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public Integer getFlag() {
    return flag;
  }

  public void setFlag(Integer flag) {
    this.flag = flag;
  }

  public ErmFieldEntity getErmFieldEntity() {
    return ermFieldEntity;
  }

  public void setErmFieldEntity(ErmFieldEntity ermFieldEntity) {
    this.ermFieldEntity = ermFieldEntity;
  }
  
  


}

