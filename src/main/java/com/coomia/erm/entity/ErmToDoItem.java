/**
 * Copyright (c) 2015-2020 Coomia Network Technology Co., Ltd.  All Rights Reserved.
 *
 * <p>
 * This software is licensed not sold.  Use or reproduction of this software by
 * any unauthorized individual or entity is strictly prohibited. This software
 * is the confidential and proprietary information of Coomia Network Technology Co., Ltd.
 * Disclosure of such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with
 * Coomia Network Technology Co., Ltd.
 *
 * <p>
 * Coomia Network Technology Co., Ltd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT.  Coomia Network Technology Co., Ltd. SHALL NOT
 * BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ANY DERIVATIVES THEREOF.
 */
package com.coomia.erm.entity;

/**
 * 流程待办信息
 * @author spancer
 * date: 2017年11月9日 上午11:49:03 <br/> 
 */
public class ErmToDoItem {
  
  //项目ID
  private int fundId;
  //项目名称
  private String fundName;
  //学校ID
  private int schId;
  //学校名称
  private String schName;
  //项目状态
  private int status;
  //项目状态
  private String fundStatus;
  //该状态下的待处理人数
  private int count;
  /**
   * @return the fundId
   */
  public int getFundId() {
    return fundId;
  }
  /**
   * @param fundId the fundId to set
   */
  public void setFundId(int fundId) {
    this.fundId = fundId;
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
   * @return the schId
   */
  public int getSchId() {
    return schId;
  }
  /**
   * @param schId the schId to set
   */
  public void setSchId(int schId) {
    this.schId = schId;
  }
  /**
   * @return the schName
   */
  public String getSchName() {
    return schName;
  }
  /**
   * @param schName the schName to set
   */
  public void setSchName(String schName) {
    this.schName = schName;
  }
  /**
   * @return the status
   */
  public int getStatus() {
    return status;
  }
  /**
   * @param status the status to set
   */
  public void setStatus(int status) {
    this.status = status;
  }
  /**
   * @return the fundStatus
   */
  public String getFundStatus() {
    return fundStatus;
  }
  /**
   * @param fundStatus the fundStatus to set
   */
  public void setFundStatus(String fundStatus) {
    this.fundStatus = fundStatus;
  }
  /**
   * @return the count
   */
  public int getCount() {
    return count;
  }
  /**
   * @param count the count to set
   */
  public void setCount(int count) {
    this.count = count;
  }
  
  
  
}
