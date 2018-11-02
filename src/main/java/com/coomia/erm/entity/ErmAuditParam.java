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

import java.io.Serializable;

/**
 * @author spancer
 * date: 2017年11月8日 下午4:08:04 <br/> 
 */
public class ErmAuditParam implements Serializable {

  /**
   * serialVersionUID:TODO
   */
  private static final long serialVersionUID = -8677724245813710979L;
  
  //学生资助ID
  private int stuFundId;
  //审核状态,0不通过， 1通过
  private int auditStatus;
  //审核意见
  private String note;
  /**
   * @return the stuFundId
   */
  public int getStuFundId() {
    return stuFundId;
  }
  /**
   * @param stuFundId the stuFundId to set
   */
  public void setStuFundId(int stuFundId) {
    this.stuFundId = stuFundId;
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
  
  
  
  

}
