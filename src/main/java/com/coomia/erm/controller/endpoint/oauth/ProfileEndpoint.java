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
package com.coomia.erm.controller.endpoint.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.common.auth.model.UserContextExt;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.model.CoomiaHttpResponse;
import com.coomia.erm.model.CoomiaHttpStatus;
import com.coomia.erm.service.ErmSchoolService;

/**
 * End-point for retrieving logged-in user details.
 * 
 * @author spancer.ray
 *
 * Aug 4, 2016
 */
@RestController
public class ProfileEndpoint {
  @Autowired
  ErmSchoolService ermSchoolService;
  
    @RequestMapping(value="/erm/api/me", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> get(JwtAuthenticationToken token) {
        UserContext currentUser =  (UserContext) token.getPrincipal();
        if (currentUser == null) {
            return new ResponseEntity<>(CoomiaHttpResponse.error(CoomiaHttpStatus.USER_NOT_LOGIN), HttpStatus.BAD_REQUEST);
        }
        else
        {
          Integer schoolId =currentUser.getSchoolId();
          if(null == schoolId || schoolId == 0)
          {
            return new ResponseEntity<>(CoomiaHttpResponse.ok(new UserContextExt(currentUser, null)), HttpStatus.OK);
          }
          else
          {
            ErmSchoolEntity sch = ermSchoolService.queryObject(schoolId);
            return new ResponseEntity<>(CoomiaHttpResponse.ok(new UserContextExt(currentUser, sch)), HttpStatus.OK);
          }
         
        }
    }
}
