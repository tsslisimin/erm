package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 指标值
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:53
 */
public class ErmFieldValEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  //
  private Integer id;
  // 字段ID关联tb_erm_field
  private Integer fieldId;
  // 选项code
  private String valKey;
  // 选项值
  private String valVal;
  // 权重
  private Double valWeight;
  private Integer flag = 0;



  public Integer getFlag() {
    return flag;
  }

  public void setFlag(Integer flag) {
    this.flag = flag;
  }

  /**
   * 设置：
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * 获取：
   */
  public Integer getId() {
    return id;
  }

  /**
   * 设置：字段ID关联tb_erm_field
   */
  public void setFieldId(Integer fieldId) {
    this.fieldId = fieldId;
  }

  /**
   * 获取：字段ID关联tb_erm_field
   */
  public Integer getFieldId() {
    return fieldId;
  }

  /**
   * 设置：选项code
   */
  public void setValKey(String valKey) {
    this.valKey = valKey;
  }

  /**
   * 获取：选项code
   */
  public String getValKey() {
    return valKey;
  }

  /**
   * 设置：选项值
   */
  public void setValVal(String valVal) {
    this.valVal = valVal;
  }

  /**
   * 获取：选项值
   */
  public String getValVal() {
    return valVal;
  }

  /**
   * 设置：权重
   */
  public void setValWeight(Double valWeight) {
    this.valWeight = valWeight;
  }

  /**
   * 获取：权重
   */
  public Double getValWeight() {
    return valWeight;
  }
}
