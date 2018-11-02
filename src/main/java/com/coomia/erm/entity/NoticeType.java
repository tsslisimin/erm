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
 * date: 2017年11月8日 下午10:27:22 <br/> 
 */
public enum NoticeType {
  NEWS(1, "消息"), TODO(2, "待办"), WFMSG(3, "审核通知");
  private int    code;
  private String name;

  private NoticeType(int code, String name) {
      this.code = code;
      this.name = name;
  }

  public int getCode() {
      return code;
  }

  public void setCode(int code) {
      this.code = code;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }
  
  
  public static NoticeType getName(int code){
    for(NoticeType fund : NoticeType.values()){
      if(fund.getCode() == code){
        return fund;
      }
    }
    return null;
  }
  
  public static String getCNName(int code){
    for(NoticeType fund : NoticeType.values()){
      if(fund.getCode() == code){
        return fund.getName();
      }
    }
    return null;
  }
}
