
 /**
 * Project Name:erm
 * File Name:ErmStudentVo.java
 * Package Name:com.coomia.erm.entity
 * Date:2018年5月5日下午5:50:15
 * Copyright (c) 2018, spancer.ray All Rights Reserved.
 *
*/

package com.coomia.erm.entity;
/**
 * ClassName:ErmStudentVo 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2018年5月5日 下午5:50:15 
 * @author   spancer
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ErmStudentVo {

  private Integer schoolId;
  
  private String name;
  
  private String idcard;
  // 年级
  private String grade;
  // 班级
  private String clazz;
  public Integer getSchoolId() {
    return schoolId;
  }
  public void setSchoolId(Integer schoolId) {
    this.schoolId = schoolId;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getIdcard() {
    return idcard;
  }
  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }
  public String getGrade() {
    return grade;
  }
  public void setGrade(String grade) {
    this.grade = grade;
  }
  public String getClazz() {
    return clazz;
  }
  public void setClazz(String clazz) {
    this.clazz = clazz;
  }
  
}

