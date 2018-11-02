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
package com.coomia.erm.common.auth.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.coomia.erm.exceptions.exception.security.AuthMethodNotSupportedException;
import com.coomia.erm.exceptions.exception.security.JwtExpiredTokenException;
import com.coomia.erm.model.CoomiaHttpResponse;
import com.coomia.erm.model.CoomiaHttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author spancer.ray
 *
 * Aug 3, 2016
 */
@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;
    
    @Autowired
    public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }	
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		if (e instanceof BadCredentialsException) {
			mapper.writeValue(response.getWriter(), CoomiaHttpResponse.error(HttpStatus.OK, CoomiaHttpStatus.AUTHENTICATION));
		} else if (e instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), CoomiaHttpResponse.error(HttpStatus.OK, CoomiaHttpStatus.JWT_TOKEN_EXPIRED));
		} else if (e instanceof AuthMethodNotSupportedException) {
		    mapper.writeValue(response.getWriter(), CoomiaHttpResponse.error(HttpStatus.OK, CoomiaHttpStatus.AUTHENTICATION));
		}
		else if (e instanceof InsufficientAuthenticationException) {
          mapper.writeValue(response.getWriter(), CoomiaHttpResponse.error(HttpStatus.OK, CoomiaHttpStatus.FAIL));
        }
		else if (e instanceof UsernameNotFoundException) {
          mapper.writeValue(response.getWriter(), CoomiaHttpResponse.error(HttpStatus.OK, CoomiaHttpStatus.USER_NOT_FOUND));
      }
		
		mapper.writeValue(response.getWriter(), CoomiaHttpResponse.of(HttpStatus.OK, CoomiaHttpStatus.AUTHENTICATION));
	}
}
