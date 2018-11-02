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
package com.coomia.erm.exceptions.handler;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coomia.erm.exceptions.exception.security.AuthMethodNotSupportedException;
import com.coomia.erm.exceptions.exception.security.InvalidJwtTokenException;
import com.coomia.erm.exceptions.exception.security.JwtExpiredTokenException;
import com.coomia.erm.model.CoomiaHttpResponse;
import com.coomia.erm.model.CoomiaHttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Object>(CoomiaHttpResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { AuthMethodNotSupportedException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> authMethodNotSupportedException(AuthMethodNotSupportedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Object>(CoomiaHttpResponse.error(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { InvalidJwtTokenException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> invalidJwtTokenException(InvalidJwtTokenException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Object>(CoomiaHttpResponse.error(HttpStatus.UNAUTHORIZED, CoomiaHttpStatus.JWT_INVALID_TOKEN, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { JwtExpiredTokenException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> jwtExpiredTokenException(JwtExpiredTokenException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Object>(CoomiaHttpResponse.error(HttpStatus.UNAUTHORIZED, CoomiaHttpStatus.JWT_TOKEN_EXPIRED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<Object> IllegalArgumentException(IllegalArgumentException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Object>(CoomiaHttpResponse.error(HttpStatus.NOT_IMPLEMENTED, ex.getMessage()), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> aAccessDeniedException(AccessDeniedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Object>(CoomiaHttpResponse.error(HttpStatus.FORBIDDEN, ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> unkownException(Exception ex) {
    	ex.printStackTrace();
        return new ResponseEntity<Object>(CoomiaHttpResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
