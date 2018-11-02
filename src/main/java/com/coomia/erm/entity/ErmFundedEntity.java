package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.constants.SystemConstants;

/**
 * 资助信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public class ErmFundedEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer schFundId;

  private Integer schoolId;

  private String schoolName;

  private Integer year;
  // 学期code
  private String semester;

  private String semesterName;

  // 国家资助项目code--码表中的dict_code值
  private String fundDictCode;
  //
  private Integer id;
  // 资助类型
  private String type;
  // 资助子类型
  private String subtype;

  private String typeName;
  //
  private String name;
  //
  private Integer ebId = SystemConstants.ebId;
  //
  private String describ;
  //
  private String creator;
  //
  private Date createTime;
  //
  private String updator;
  //
  private Date updateTime;

  private Integer status;
  // 审批状态字符串
  private String statusName;

  private String formUrl;
  
  private String schzone;
  
  private String level;
  
  private Integer publicStatus;
  
  private String publicImg;

  private Double totalMoney;
      private Integer count;


  public String getSchzone() {
    return schzone;
  }

  public void setSchzone(String schzone) {
    this.schzone = schzone;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
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
   * 设置：资助类型
   */
  public void setType(String type) {
    this.type = type;
    this.typeName = DictConstants.FUNDED_TYPE_MAP.get(Integer.parseInt(type));
  }

  /**
   * 获取：资助类型
   */
  public String getType() {
    return type;
  }

  /**
   * 设置：
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获取：
   */
  public String getName() {
    return name;
  }

  /**
   * 设置：
   */
  public void setEbId(Integer ebId) {
    this.ebId = ebId;
  }

  /**
   * 获取：
   */
  public Integer getEbId() {
    return ebId;
  }

  /**
   * 设置：
   */
  public void setDescrib(String describ) {
    this.describ = describ;
  }

  /**
   * 获取：
   */
  public String getDescrib() {
    return describ;
  }

  /**
   * 设置：
   */
  public void setCreator(String creator) {
    this.creator = creator;
  }

  /**
   * 获取：
   */
  public String getCreator() {
    return creator;
  }

  /**
   * 设置：
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * 获取：
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * 设置：
   */
  public void setUpdator(String updator) {
    this.updator = updator;
  }

  /**
   * 获取：
   */
  public String getUpdator() {
    return updator;
  }

  /**
   * 设置：
   */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  /**
   * 获取：
   */
  public Date getUpdateTime() {
    return updateTime;
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
    this.statusName =
        FundStatus.getName(status) == null ? "" : FundStatus.getName(status).getName();
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
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

  public String getFormUrl() {
    return formUrl;
  }

  public void setFormUrl(String formUrl) {
    this.formUrl = formUrl;
  }

  /**
   * @return the schoolName
   */
  public String getSchoolName() {
    return schoolName;
  }

  /**
   * @param schoolName the schoolName to set
   */
  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  /**
   * @return the schoolId
   */
  public Integer getSchoolId() {
    return schoolId;
  }

  /**
   * @param schoolId the schoolId to set
   */
  public void setSchoolId(Integer schoolId) {
    this.schoolId = schoolId;
  }

  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }

  public String getSubtype() {
    return subtype;
  }

  public void setSubtype(String subtype) {
    this.subtype = subtype;
  }

  public String getFundDictCode() {
    return fundDictCode;
  }

  public void setFundDictCode(String fundDictCode) {
    this.fundDictCode = fundDictCode;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getSemesterName() {
    return semesterName;
  }

  public void setSemesterName(String semesterName) {
    this.semesterName = semesterName;
  }

  public Integer getPublicStatus() {
	return publicStatus;
  }

  public void setPublicStatus(Integer publicStatus) {
	this.publicStatus = publicStatus;
  }

  public String getPublicImg() {
	return publicImg;
  }

  public void setPublicImg(String publicImg) {
	this.publicImg = publicImg;
  }


  public Double getTotalMoney() {
    return totalMoney;
  }

  public void setTotalMoney(Double totalMoney) {
    this.totalMoney = totalMoney;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
