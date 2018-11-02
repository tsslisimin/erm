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
package com.coomia.erm.common.auth.model;

import com.coomia.erm.entity.ErmSchoolEntity;

/**
 * @author spancer
 * date: 2017年11月21日 上午10:33:47 <br/> 
 */
public class UserContextExt {

  private  UserContext user;
  private  ErmSchoolEntity school;
  /**
   * @return the user
   */
  public UserContext getUser() {
    return user;
  }
  /**
   * @return the school
   */
  public ErmSchoolEntity getSchool() {
    return school;
  }
  /**
   * @param user
   * @param school
   */
  public UserContextExt(UserContext user, ErmSchoolEntity school) {
    super();
    this.user = user;
    this.school = school;
  }
  
  
}
