package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Set;


/**
 * 学生指标值
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:52
 */
public class ErmStuValueEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  //
  private Integer id;
  private Integer flag =1;
  //
  private Integer stuId;
  //
  private Integer fieldId;
  //
  private String fieldValVal;
  //
  private String fieldValKey;
  //
  private Integer schfundId;

  private Set<String> fieldValKeyList;

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
   * 设置：
   */
  public void setStuId(Integer stuId) {
    this.stuId = stuId;
  }

  /**
   * 获取：
   */
  public Integer getStuId() {
    return stuId;
  }

  /**
   * 设置：
   */
  public void setFieldId(Integer fieldId) {
    this.fieldId = fieldId;
  }

  /**
   * 获取：
   */
  public Integer getFieldId() {
    return fieldId;
  }

  /**
   * 设置：
   */
  public void setFieldValVal(String fieldValVal) {
    this.fieldValVal = fieldValVal;
  }

  /**
   * 获取：
   */
  public String getFieldValVal() {
    return fieldValVal;
  }

  /**
   * 设置：
   */
  public void setFieldValKey(String fieldValKey) {
    this.fieldValKey = fieldValKey;
  }

  /**
   * 获取：
   */
  public String getFieldValKey() {
    return fieldValKey;
  }

  /**
   * 设置：
   */
  public void setSchfundId(Integer schfundId) {
    this.schfundId = schfundId;
  }

  /**
   * 获取：
   */
  public Integer getSchfundId() {
    return schfundId;
  }

  public Set<String> getFieldValKeyList() {
    return fieldValKeyList;
  }

  public void setFieldValKeyList(Set<String> fieldValKeyList) {
    this.fieldValKeyList = fieldValKeyList;
  }

  public Integer getFlag() {
    return flag;
  }

  public void setFlag(Integer flag) {
    this.flag = flag;
  }


}
