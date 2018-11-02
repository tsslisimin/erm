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

/**
 * @author spancer date: 2017年11月21日 下午4:23:54 <br/>
 */
public class ErmAuditParamExt {

  private int schFundId;
  // 审核状态,0不通过， 1通过
  private int auditStatus;
  // 审核意见
  private String note;
  //总资助金额
  private double money;

  /**
   * @return the schFundId
   */
  public int getSchFundId() {
    return schFundId;
  }

  /**
   * @param schFundId the schFundId to set
   */
  public void setSchFundId(int schFundId) {
    this.schFundId = schFundId;
  }

  /**
   * @return the auditStatus
   */
  public int getAuditStatus() {
    return auditStatus;
  }

  /**
   * @param auditStatus the auditStatus to set
   */
  public void setAuditStatus(int auditStatus) {
    this.auditStatus = auditStatus;
  }

  /**
   * @return the note
   */
  public String getNote() {
    return note;
  }

  /**
   * @param note the note to set
   */
  public void setNote(String note) {
    this.note = note;
  }

  /**
   * @return the money
   */
  public double getMoney() {
    return money;
  }

  /**
   * @param money the money to set
   */
  public void setMoney(double money) {
    this.money = money;
  }
  

}
