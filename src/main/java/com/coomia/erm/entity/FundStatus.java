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
 * date: 2017年11月5日 下午10:27:22 <br/> 
 */
public enum FundStatus {
  NEW(1, "新建"),  RUNNING(2, "运行中"), CONFIG(3, "项目配置"),
  FDCONFIRM(4, "财政部门确定资助计划"), APPLY(5, "申请表填写"),CLASSAUDIT(6, "班级评议小组认定"), OPERAUDIT(7, "资助名单拟定"), 
  SCHAUDIT(8, "学校评审委员会认定"), SCHANNOUNCEMENT(9, "学校公示"),EBAUDIT(10, "教育局审批"),
  CARDINPUT(11, "银行卡号录入"),
  CARDCOMPARE(12, "银行卡号比对"),OFFERRED(13, "资助发放"), NOTICE(14, "资助已发放"),
  CLOSE(15, "项目结束")
  ,OPERAUDITFAIL(16, "操作员打回"),SCHAUDITFAIL(17, "校长打回"),EBAUDITFAIL(18, "教育局打回"),
  OPERAUDITSUCCESS(19, "已经年级评议小组初审"),SCHAUDITSUCCESS(20, "已经学校评审委员会认定"),EBAUDITSUCCESS(21, "教育局审核通过"),
  CCFAIL(22, "银行卡比对失败"),CCSUCCESS(23, "银行卡比对成功"),
  OFFERRING(24, "尚未发放请等待"), CLASSAUDITSUCCESS(25, "已经班级评议小组认定"),CLASSAUDITFAIL(26, "班级评议小组认定未通过"),
  SCHANNOUNCEMENTSUCCESS(27, "公示无异议"),SCHANNOUNCEMENTFAIL(28,"公示有异议"),
  CARDINPUTSUCCESS(29, "银行卡号录入成功"),CARDINPUTFAIL(30,"银行卡号录入失败"),OFFERREDSUCCESS(31,"资助发放成功"),OFFERREDFAIL(32,"资助发放失败")
  ;
  private int    code;
  private String name;

  private FundStatus(int code, String name) {
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
  
  
  public static FundStatus getName(int code){
    for(FundStatus fund : FundStatus.values()){
      if(fund.getCode() == code){
        return fund;
      }
    }
    return null;
  }
  
  public static String getCNName(int code){
    for(FundStatus fund : FundStatus.values()){
      if(fund.getCode() == code){
        return fund.getName();
      }
    }
    return null;
  }
  
}
