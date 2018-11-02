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

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 * 
 * @author spancer.ray
 *         Aug 3, 2016
 */
public enum CoomiaHttpStatus {
    GLOBAL(2, "Global error"), 
    AUTHENTICATION(10, "认证失败"),
    JWT_TOKEN_EXPIRED(11, "token已失效 "), 
    JWT_INVALID_TOKEN(12, "无效的token"), 
    OK(200, "调用成功"), 
    FAIL(-200, "权限不足"), 
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"), 
    USER_NOT_FOUND(-1002, "用户不存在"), 
    USER_NOT_LOGIN(-1003, "用户未登录"),
    ACTIVITY_NOT_FOUNT(-1004, "活动不存在"),
    FILE_IS_EMPTY(-1005, "文件为空"),
    FILE_UPLOAD_ERROR(-1006, "文件上传失败"), 
    SIGN_UP_FAIL(-1007, "注册失败");
   
    private int    code;
    private String msg;

    private CoomiaHttpStatus(int errorCode, String message) {
        this.code = errorCode;
        this.msg = message;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @JsonValue
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * Whether this status code is in the HTTP series
     * {@link org.springframework.http.HttpStatus.Series#INFORMATIONAL}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is1xxInformational() {
        return Series.INFORMATIONAL.equals(series());
    }

    /**
     * Whether this status code is in the HTTP series
     * {@link org.springframework.http.HttpStatus.Series#SUCCESSFUL}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is2xxSuccessful() {
        return Series.SUCCESSFUL.equals(series());
    }

    /**
     * Whether this status code is in the HTTP series
     * {@link org.springframework.http.HttpStatus.Series#REDIRECTION}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is3xxRedirection() {
        return Series.REDIRECTION.equals(series());
    }

    /**
     * Whether this status code is in the HTTP series
     * {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is4xxClientError() {
        return Series.CLIENT_ERROR.equals(series());
    }

    /**
     * Whether this status code is in the HTTP series
     * {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is5xxServerError() {
        return Series.SERVER_ERROR.equals(series());
    }

    /**
     * Returns the HTTP status series of this status code.
     * 
     * @see HttpStatus.Series
     */
    public Series series() {
        return Series.valueOf(this);
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return Integer.toString(this.code);
    }

    /**
     * Return the enum constant of this type with the specified numeric value.
     * 
     * @param statusCode
     *            the numeric value of the enum to be returned
     * @return the enum constant with the specified numeric value
     * @throws IllegalArgumentException
     *             if this enum has no constant for the specified numeric value
     */
    public static CoomiaHttpStatus valueOf(int statusCode) {
        for (CoomiaHttpStatus status : values()) {
            if (status.code == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }

    /**
     * Enumeration of HTTP status series.
     * <p>
     * Retrievable via {@link HttpStatus#series()}.
     */
    public enum Series {
        INFORMATIONAL(1), SUCCESSFUL(2), REDIRECTION(3), CLIENT_ERROR(4), SERVER_ERROR(5);
        private final int value;

        Series(int value) {
            this.value = value;
        }

        /**
         * Return the integer value of this status series. Ranges from 1 to 5.
         */
        public int value() {
            return this.value;
        }

        public static Series valueOf(int status) {
            int seriesCode = status / 100;
            for (Series series : values()) {
                if (series.value == seriesCode) {
                    return series;
                }
            }
            throw new IllegalArgumentException("No matching constant for [" + status + "]");
        }

        public static Series valueOf(CoomiaHttpStatus status) {
            return valueOf(status.code);
        }
    }
}
