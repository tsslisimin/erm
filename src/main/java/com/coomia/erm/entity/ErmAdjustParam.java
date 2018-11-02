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

import java.io.Serializable;
import java.util.List;

/**
 * @author spancer date: 2017年11月8日 下午4:08:04 <br/>
 */
public class ErmAdjustParam implements Serializable {

  /**
   * serialVersionUID:TODO
   * 
   * @since JDK 1.6
   */
  private static final long serialVersionUID = 4069524914540026399L;

  private Integer schId;
  // 学生ID
  private List<Integer> stuIds;
  // 调整的目标等级
  private int diffLevel;
  // 审核意见
  private String note;

  public List<Integer> getStuIds() {
    return stuIds;
  }

  public void setStuIds(List<Integer> stuIds) {
    this.stuIds = stuIds;
  }

  public int getDiffLevel() {
    return diffLevel;
  }

  public void setDiffLevel(int diffLevel) {
    this.diffLevel = diffLevel;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Integer getSchId() {
    return schId;
  }

  public void setSchId(Integer schId) {
    this.schId = schId;
  }


}
