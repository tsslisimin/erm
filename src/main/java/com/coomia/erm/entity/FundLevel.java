
/**
 * Project Name:erm File Name:FundLevel.java Package Name:com.coomia.erm.entity
 * Date:2018年3月5日下午9:44:02 Copyright (c) 2018, spancer.ray All Rights Reserved.
 *
 */

package com.coomia.erm.entity;

/**
 * ClassName:FundLevel Function: TODO ADD FUNCTION. Reason: TODO ADD REASON. Date: 2018年3月5日
 * 下午9:44:02
 * 
 * @author spancer
 * @version
 * @since JDK 1.6
 * @see
 */
public class FundLevel {

  // 学校项目-学区（小学/初中）
  private String schzone;
  // 该档次的级别
  private String levelCode;

  // 该档次的名额
  private Integer levelCount;
  // 资助金额
  private Double levelMoney;

  public String getLevelCode() {
    return levelCode;
  }

  public void setLevelCode(String levelCode) {
    this.levelCode = levelCode;
  }

  public Integer getLevelCount() {
    return levelCount;
  }

  public void setLevelCount(Integer levelCount) {
    this.levelCount = levelCount;
  }

  public Double getLevelMoney() {
    return levelMoney;
  }

  public void setLevelMoney(Double levelMoney) {
    this.levelMoney = levelMoney;
  }

  public String getSchzone() {
    return schzone;
  }

  public void setSchzone(String schzone) {
    this.schzone = schzone;
  }
  
  
}

