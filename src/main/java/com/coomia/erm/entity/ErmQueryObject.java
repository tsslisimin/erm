/**
 * Copyright (c) 2015-2020 Coomia Network Technology Co., Ltd. All Rights Reserved.
 *
 * <p>
 * This software is licensed not sold. Use or reproduction of this software by any unauthorized
 * individual or entity is strictly prohibited. This software is the confidential and proprietary
 * information of Coomia Network Technology Co., Ltd. Disclosure of such confidential information
 * and shall use it only in accordance with the terms of the license agreement you entered into with
 * Coomia Network Technology Co., Ltd.
 *
 * <p>
 * Coomia Network Technology Co., Ltd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY
 * OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Coomia Network
 * Technology Co., Ltd. SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ANY DERIVATIVES THEREOF.
 */
package com.coomia.erm.entity;

import java.util.Set;

import com.coomia.erm.constants.SystemConstants;

/**
 * @author spancer date: 2017年11月11日 下午5:22:33 <br/>
 */
public class ErmQueryObject {
  
  private Integer stuId;

  private Integer age;

  private Integer ebId = SystemConstants.ebId;

  private Integer schId;

  private Integer fundId;

  private Integer schFundId;

  private Integer status;

  // 姓名
  private String stuName;

  private String fundName;

  // 身份证号
  private String idCard;

  private String stuno;
  // 年级
  private String grade;
  // 班级
  private String clazz;

  private Integer diffLevel;

  private Integer isGraduated;

  private Integer flag;

  private Integer teacheThecked;
  // 档次
  private String level;
  // 学区
  private String schzone;

  // 年份
  private Integer year;
  // 学期
  private String semester;

  private Set<Integer> statusSet;


  private Integer gender;

private  Integer minAge;
private Integer maxAge;

private Integer hasApplyImg;

private String isPoor;

private String address;
private String school;

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public Integer getMinAge() {
    return minAge;
  }

  public void setMinAge(Integer minAge) {
    this.minAge = minAge;
  }

  public Integer getMaxAge() {
    return maxAge;
  }

  public void setMaxAge(Integer maxAge) {
    this.maxAge = maxAge;
  }

  public Integer getHasApplyImg() {
    return hasApplyImg;
  }

  public void setHasApplyImg(Integer hasApplyImg) {
    this.hasApplyImg = hasApplyImg;
  }

  public String getIsPoor() {
    return isPoor;
  }

  public void setIsPoor(String isPoor) {
    this.isPoor = isPoor;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getSchzone() {
    return schzone;
  }

  public void setSchzone(String schzone) {
    this.schzone = schzone;
  }

  public Integer getIsGraduated() {
    return isGraduated;
  }

  public void setIsGraduated(Integer isGraduated) {
    this.isGraduated = isGraduated;
  }

  public Integer getFlag() {
    return flag;
  }

  public void setFlag(Integer flag) {
    this.flag = flag;
  }

  public Integer getTeacheThecked() {
    return teacheThecked;
  }

  public void setTeacheThecked(Integer teacheThecked) {
    this.teacheThecked = teacheThecked;
  }

  public Integer getDiffLevel() {
    return diffLevel;
  }

  public void setDiffLevel(Integer diffLevel) {
    this.diffLevel = diffLevel;
  }


  // 当前页码
  private int page = 1;
  // 每页条数
  private int limit = 10;



  public Set<Integer> getStatusSet() {
    return statusSet;
  }

  public void setStatusSet(Set<Integer> statusSet) {
    this.statusSet = statusSet;
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

  /**
   * @return the page
   */
  public int getPage() {
    return page;
  }

  /**
   * @param page the page to set
   */
  public void setPage(int page) {
    this.page = page;
  }

  /**
   * @return the limit
   */
  public int getLimit() {
    return limit;
  }

  /**
   * @param limit the limit to set
   */
  public void setLimit(int limit) {
    this.limit = limit;
  }

  /**
   * @return the schId
   */
  public Integer getSchId() {
    return schId;
  }

  /**
   * @param schId the schId to set
   */
  public void setSchId(Integer schId) {
    this.schId = schId;
  }

  /**
   * @return the fundId
   */
  public Integer getFundId() {
    return fundId;
  }

  /**
   * @param fundId the fundId to set
   */
  public void setFundId(Integer fundId) {
    this.fundId = fundId;
  }

  /**
   * @return the schFundId
   */
  public Integer getSchFundId() {
    return schFundId;
  }

  /**
   * @param schFundId the schFundId to set
   */
  public void setSchFundId(Integer schFundId) {
    this.schFundId = schFundId;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @return the stuName
   */
  public String getStuName() {
    return stuName;
  }

  /**
   * @param stuName the stuName to set
   */
  public void setStuName(String stuName) {
    this.stuName = stuName;
  }

  /**
   * @return the fundName
   */
  public String getFundName() {
    return fundName;
  }

  /**
   * @param fundName the fundName to set
   */
  public void setFundName(String fundName) {
    this.fundName = fundName;
  }

  /**
   * @return the idCard
   */
  public String getIdCard() {
    return idCard;
  }

  /**
   * @param idCard the idCard to set
   */
  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  /**
   * @return the stuno
   */
  public String getStuno() {
    return stuno;
  }

  /**
   * @param stuno the stuno to set
   */
  public void setStuno(String stuno) {
    this.stuno = stuno;
  }

  /**
   * @return the ebId
   */
  public Integer getEbId() {
    return ebId;
  }

  /**
   * @param ebId the ebId to set
   */
  public void setEbId(Integer ebId) {
    this.ebId = ebId;
  }


  /**
   * @return the stuId
   */
  public Integer getStuId() {
    return stuId;
  }

  /**
   * @param stuId the stuId to set
   */
  public void setStuId(Integer stuId) {
    this.stuId = stuId;
  }

  /**
   * @return the age
   */
  public Integer getAge() {
    return age;
  }

  /**
   * @param age the age to set
   */
  public void setAge(Integer age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
