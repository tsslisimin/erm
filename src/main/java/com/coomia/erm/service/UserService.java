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
package com.coomia.erm.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.SystemConstants;
import com.coomia.erm.dao.ErmAdminDao;
import com.coomia.erm.entity.ErmAdminEntity;

/**
 * Mock implementation.
 * 
 * @author spancer.ray Aug 4, 2016
 */
@Service
public class UserService {

  @Autowired
  private ErmAdminDao ermAdminDao;

  /**
   * @param username
   * @return
   */
  // 获取该教育局的用户,默认带教育局ID， 这个ID配置在application.yml文件中
  public Optional<ErmAdminEntity> getByUsername(String username) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("username", username);
    param.put("ebId", SystemConstants.ebId);
    ErmAdminEntity admin = this.ermAdminDao.queryObjectByName(param);
    if(null != admin)
    {
      
      Optional<ErmAdminEntity> optionAdmin = Optional.of(admin);
      return optionAdmin;
    }
    else
      return null;
  }

  // 获取该教育局的用户,默认带教育局ID， 这个ID配置在application.yml文件中
  public ErmAdminEntity getByUser(String username) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("username", username);
    param.put("ebId", SystemConstants.ebId);
    return this.ermAdminDao.queryObjectByName(param);
  }


}
