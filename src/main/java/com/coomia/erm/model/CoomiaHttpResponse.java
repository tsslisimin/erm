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
package com.coomia.erm.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * spancer.ray on 2017/2/7.
 * 自定义的返回结果实体model
 */
public class CoomiaHttpResponse {
    /**
     * http 状态码
     */
    private int        status;
    /**
     * 业务状态码
     */
    private int        code;
    /**
     * 返回信息描述
     */
    private String     message;
    /**
     * 返回数据
     */
    private Object     data;
    private final Date timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // 提供几种构造方法
    public CoomiaHttpResponse(int status, int code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = new java.util.Date();
    }


    public CoomiaHttpResponse(HttpStatus httpStatus, CoomiaHttpStatus resultStatus, Object data) {
        this.status = httpStatus.value();
        if (null == resultStatus) {
            this.code = httpStatus.value();
            this.message = httpStatus.getReasonPhrase();
        } else {
            this.code = resultStatus.getCode();
            this.message = resultStatus.getMsg();
        }
        if(null == data)
            this.data = "";
        else
            this.data = data;
        this.timestamp = new java.util.Date();
    }

    // 请求成功的方法
    /**
     * 有数据返回时的请求成功方法
     * 
     * @param data
     *            请求成功的数据
     * @return 自定义的ResultModel
     */
    public static CoomiaHttpResponse ok(Object data) {
        return new CoomiaHttpResponse(HttpStatus.OK, CoomiaHttpStatus.OK, data);
    }

    /**
     * 无数据返回时的请求成功方法
     * 
     * @return 自定义的ResultModel
     */
    public static CoomiaHttpResponse ok() {
        return new CoomiaHttpResponse(HttpStatus.OK, CoomiaHttpStatus.OK, null);
    }

    // 请求失败的方法
    /**
     * http请求成功，但业务请求失败时
     * 
     * @param error
     *            错误状态
     * @return 自定义的ResultModel
     */
    public static CoomiaHttpResponse error(CoomiaHttpStatus coomiaStatus) {
        return new CoomiaHttpResponse(HttpStatus.OK, coomiaStatus, null);
    }

    /**
     * 请求失败时
     * 
     * @param error
     *            错误状态
     * @return 自定义的ResultModel
     */
    public static CoomiaHttpResponse error(HttpStatus httpstatus, CoomiaHttpStatus coomiaStatus) {
        return new CoomiaHttpResponse(httpstatus, coomiaStatus, null);
    }

    public static CoomiaHttpResponse error(HttpStatus httpstatus, Object data) {
        return new CoomiaHttpResponse(httpstatus, null, data);
    }
    
    public static CoomiaHttpResponse error(HttpStatus httpstatus,CoomiaHttpStatus coomiaStatus, Object data) {
        return new CoomiaHttpResponse(httpstatus, coomiaStatus, data);
    }

    public static CoomiaHttpResponse error(HttpStatus httpstatus) {
        return new CoomiaHttpResponse(httpstatus, null, null);
    }

    /**
     * @param httpstatus
     * @param coomiaStatus
     * @return
     */
    public static CoomiaHttpResponse of(HttpStatus httpstatus, CoomiaHttpStatus coomiaStatus) {
        return new CoomiaHttpResponse(httpstatus, coomiaStatus, null);
    }

    /**
     * @param httpstatus
     * @param coomiaStatus
     * @param data
     * @return
     */
    public static CoomiaHttpResponse of(HttpStatus httpstatus, CoomiaHttpStatus coomiaStatus, Object data) {
        return new CoomiaHttpResponse(httpstatus, coomiaStatus, data);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
