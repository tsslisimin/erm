/**
 * Project Name:erm File Name:ErmSchFundExt.java Package Name:com.coomia.erm.entity
 * Date:2018年3月5日下午9:37:52 Copyright (c) 2018, spancer.ray All Rights Reserved.
 *
 */

package com.coomia.erm.entity;

import java.util.Date;
import java.util.List;

/**
 * 用于扩展的类 ClassName:ErmSchFundExt Function: TODO ADD FUNCTION. Reason: TODO ADD REASON. Date:
 * 2018年3月5日 下午9:37:52
 * 
 * @author spancer
 * @version
 * @since JDK 1.6
 * @see
 */
public class ErmSchFundExt {
  private boolean isAll;
  private Integer fundedId;
  private List<Integer> schIdList;
  private Integer count;
  private List<FundLevel> levelList;
  private String note;
  private String creator;
  private Date createDate;
  private String updator;
  private Date updateDate;


  public boolean isAll() {
    return isAll;
  }

  public void setAll(boolean isAll) {
    this.isAll = isAll;
  }


  public String getUpdator() {
    return updator;
  }

  public void setUpdator(String updator) {
    this.updator = updator;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Integer getFundedId() {
    return fundedId;
  }

  public void setFundedId(Integer fundedId) {
    this.fundedId = fundedId;
  }

  public List<Integer> getSchIdList() {
    return schIdList;
  }

  public void setSchIdList(List<Integer> schIdList) {
    this.schIdList = schIdList;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public List<FundLevel> getLevelList() {
    return levelList;
  }

  public void setLevelList(List<FundLevel> levelList) {
    this.levelList = levelList;
  }



}

