
/**
 * Project Name:erm File Name:ErmToFundParam.java Package Name:com.coomia.erm.entity
 * Date:2018年3月15日下午10:53:58 Copyright (c) 2018, spancer.ray All Rights Reserved.
 *
 */

package com.coomia.erm.entity;

import java.util.List;

/**
 * ClassName:ErmToFundParam Function: TODO ADD FUNCTION. Reason: TODO ADD REASON. Date: 2018年3月15日
 * 下午10:53:58
 * 
 * @author spancer
 * @version
 * @since JDK 1.6
 * @see
 */
public class ErmToFundParam {

  private Integer schFundId;
  // 学生ID
  private List<Integer> stuIds;

  // 是否通过，默认都是为true.
  private boolean isPass = true;

  // 审批时的调整意见，不通过的时候才会填
  private String remark;
  // 校长端表示FundStatus.SCHANNOUNCEMENT的流程标志。
  private boolean toPublic = false;

  // 教育局银行卡比对后，标志位，表示FundStatus.OFFERRED的流程标志。
  public boolean toOffer = false;

  public boolean isToOffer() {
    return toOffer;
  }

  public void setToOffer(boolean toOffer) {
    this.toOffer = toOffer;
  }

  public boolean isToPublic() {
    return toPublic;
  }

  public void setToPublic(boolean toPublic) {
    this.toPublic = toPublic;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public boolean isPass() {
    return isPass;
  }

  public void setPass(boolean isPass) {
    this.isPass = isPass;
  }

  public Integer getSchFundId() {
    return schFundId;
  }

  public void setSchFundId(Integer schFundId) {
    this.schFundId = schFundId;
  }

  public List<Integer> getStuIds() {
    return stuIds;
  }

  public void setStuIds(List<Integer> stuIds) {
    this.stuIds = stuIds;
  }



}

