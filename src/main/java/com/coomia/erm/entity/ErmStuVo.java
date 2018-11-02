
/**
 * Project Name:erm File Name:ErmStudentVo.java Package Name:com.coomia.erm.entity
 * Date:2018年5月5日下午5:50:15 Copyright (c) 2018, spancer.ray All Rights Reserved.
 *
 */

package com.coomia.erm.entity;

/**
 * ClassName:ErmStudentVo Function: TODO ADD FUNCTION. Reason: TODO ADD REASON. Date: 2018年5月5日
 * 下午5:50:15
 * 
 * @author spancer
 * @version
 * @since JDK 1.6
 * @see
 */
public class ErmStuVo implements Comparable<ErmStuVo> {


  private String name;

  private String idcard;

  
  public ErmStuVo(String name, String idcard) {
    super();
    this.name = name;
    this.idcard = idcard;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  @Override
  public int compareTo(ErmStuVo o) {

    return this.name.compareTo(o.getName()) + this.idcard.compareTo(o.getIdcard());
  }



}

