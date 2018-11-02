package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 项目资助
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
public class ErmSchoolFundedEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  //
  private String creator;
  //
  private Date createTime;
  //
  private String updator;
  //
  private Date updateTime;
  //
  private Integer id;
  //
  private Integer fundedId;
  //
  private Integer schoolId;
  // 资助名额个数
  private Integer count;

  private Double totalMoney;

  private String formUrl;
  // 项目|资助年份
  private Integer year;
  // 学期
  private String semester;
  //学期如：2017年春季
  private String semesterName;
  //
  private Integer status;

  private String statusName;
  //
  private Date startdate;
  //
  private Date enddate;

  private ErmSchoolEntity school;

  private String schoolName;

  private ErmFundedEntity funded;

  private String fundedName;

  private List<Map<String, Object>> schoolFundedFields;

  private List<ErmSchFieldEntity> schFundFields;
  // 多个学校id串
  private String schoolIds;
  
  private List<Integer> schIdList;
  // 备注
  private String note;
  // 是否全部
  private boolean isAll;
  // 公示图片
  private String publicUrl;

  // 学校项目-学区（小学/初中）
  private String schzone;
  // 学区名称 ，供页面展示用
  private String schzoneName;

  // 资助级别
  private String level;
  // 资助级别，供页面展示用
  private String levelName;
  //公示日期
  private Date publicDate;
  //公示状态
  private Integer publicStatus;
  //公示图片
  private String publicImg;



  public List<Integer> getSchIdList() {
    return schIdList;
  }

  public void setSchIdList(List<Integer> schIdList) {
    this.schIdList = schIdList;
  }

  public String getSemesterName() {
    return semesterName;
  }

  public void setSemesterName(String semesterName) {
    this.semesterName = semesterName;
  }

  public String getSchzoneName() {
    return schzoneName;
  }

  public void setSchzoneName(String schzoneName) {
    this.schzoneName = schzoneName;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
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

  /**
   * @return the schFundFields
   */
  public List<ErmSchFieldEntity> getSchFundFields() {
    return schFundFields;
  }

  /**
   * @param schFundFields the schFundFields to set
   */
  public void setSchFundFields(List<ErmSchFieldEntity> schFundFields) {
    this.schFundFields = schFundFields;
  }

  /**
   * @return the statusName
   */
  public String getStatusName() {
    return FundStatus.getCNName(this.status);
  }

  /**
   * @param statusName the statusName to set
   */
  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  /**
   * @return the totalMoney
   */
  public Double getTotalMoney() {
    return totalMoney;
  }

  /**
   * @param totalMoney the totalMoney to set
   */
  public void setTotalMoney(Double totalMoney) {
    this.totalMoney = totalMoney;
  }

  /**
   * @return the formUrl
   */
  public String getFormUrl() {
    return formUrl;
  }

  /**
   * @param formUrl the formUrl to set
   */
  public void setFormUrl(String formUrl) {
    this.formUrl = formUrl;
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
  public void setFundedId(Integer fundedId) {
    this.fundedId = fundedId;
  }

  /**
   * 获取：
   */
  public Integer getFundedId() {
    return fundedId;
  }

  /**
   * 设置：
   */
  public void setSchoolId(Integer schoolId) {
    this.schoolId = schoolId;
  }

  /**
   * 获取：
   */
  public Integer getSchoolId() {
    return schoolId;
  }

  /**
   * 设置：资助名额个数
   */
  public void setCount(Integer count) {
    this.count = count;
  }

  /**
   * 获取：资助名额个数
   */
  public Integer getCount() {
    return count;
  }

  /**
   * 设置：项目|资助年份
   */
  public void setYear(Integer year) {
    this.year = year;
  }

  /**
   * 获取：项目|资助年份
   */
  public Integer getYear() {
    return year;
  }

  /**
   * 设置：学期
   */
  public void setSemester(String semester) {
    this.semester = semester;
  }

  /**
   * 获取：学期
   */
  public String getSemester() {
    return semester;
  }

  /**
   * 设置：
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * 获取：
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * 设置：
   */
  public void setStartdate(Date startdate) {
    this.startdate = startdate;
  }

  /**
   * 获取：
   */
  public Date getStartdate() {
    return startdate;
  }

  /**
   * 设置：
   */
  public void setEnddate(Date enddate) {
    this.enddate = enddate;
  }

  /**
   * 获取：
   */
  public Date getEnddate() {
    return enddate;
  }

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  public void setSchool(ErmSchoolEntity school) {
    this.school = school;
    this.schoolName = school.getName();
  }

  public String getFundedName() {
    return fundedName;
  }

  public void setFundedName(String fundedName) {
    this.fundedName = fundedName;
  }

  public void setFunded(ErmFundedEntity funded) {
    this.funded = funded;
    this.fundedName = funded.getName();
  }

  public List<Map<String, Object>> getSchoolFundedFields() {
    return schoolFundedFields;
  }

  public void setSchoolFundedFields(List<Map<String, Object>> schoolFundedFields) {
    this.schoolFundedFields = schoolFundedFields;
  }

  public String getSchoolIds() {
    return schoolIds;
  }

  public void setSchoolIds(String schoolIds) {
    this.schoolIds = schoolIds;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public boolean isAll() {
    return isAll;
  }

  public void setAll(boolean isAll) {
    this.isAll = isAll;
  }

  public String getPublicUrl() {
    return publicUrl;
  }

  public void setPublicUrl(String publicUrl) {
    this.publicUrl = publicUrl;
  }

  public Date getPublicDate() {
	return publicDate;
  }

  public void setPublicDate(Date publicDate) {
	this.publicDate = publicDate;
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
  
}
