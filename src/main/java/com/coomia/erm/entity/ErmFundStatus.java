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
 * @author spancer
 * date: 2017年11月25日 上午12:12:54 <br/> 
 */
public class ErmFundStatus {

  private int statusCode;
  private String statusName;
  /**
   * @return the statusCode
   */
  public int getStatusCode() {
    return statusCode;
  }
  /**
   * @param statusCode the statusCode to set
   */
  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
  /**
   * @return the statusName
   */
  public String getStatusName() {
    return statusName;
  }
  /**
   * @param statusName the statusName to set
   */
  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }
  /**
   * @param statusCode
   * @param statusName
   */
  public ErmFundStatus(int statusCode, String statusName) {
    super();
    this.statusCode = statusCode;
    this.statusName = statusName;
  }
  
}
