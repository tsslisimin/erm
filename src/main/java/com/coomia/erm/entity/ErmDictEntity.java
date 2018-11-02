package com.coomia.erm.entity;

import java.io.Serializable;


/**
 * 字典表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-08 23:37:37
 */
public class ErmDictEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  //
  private Integer id;
  //
  private Integer type;
  //
  private String dictCode;
  //
  private String dictName;

  private String status;

  public ErmDictEntity() {}

  public ErmDictEntity(Integer type, String dictCode, String dictName) {
    this.type = type;
    this.dictCode = dictCode;
    this.dictName = dictName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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
   * 设置：
   */
  public void setType(Integer type) {
    this.type = type;
  }

  /**
   * 获取：
   */
  public Integer getType() {
    return type;
  }

  /**
   * 设置：
   */
  public void setDictCode(String dictCode) {
    this.dictCode = dictCode;
  }

  /**
   * 获取：
   */
  public String getDictCode() {
    return dictCode;
  }

  /**
   * 设置：
   */
  public void setDictName(String dictName) {
    this.dictName = dictName;
  }

  /**
   * 获取：
   */
  public String getDictName() {
    return dictName;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((dictCode == null) ? 0 : dictCode.hashCode());
    result = prime * result + ((dictName == null) ? 0 : dictName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object otherObject) {

    // a quick test tot see if the objects are identical
    if (this == otherObject)
      return true;

    // must return false if the explicit parameter is null
    if (otherObject == null)
      return false;

    // if the classes don't match, they can't be equal
    if (getClass() != otherObject.getClass())
      return false;

    // now we know otherObject is non-null Employee
    ErmDictEntity other = (ErmDictEntity) otherObject;

    // test whether the fields have identical values
    return type.equals(other.type) && dictCode == other.dictCode && dictName.equals(other.dictName);
  }
}
