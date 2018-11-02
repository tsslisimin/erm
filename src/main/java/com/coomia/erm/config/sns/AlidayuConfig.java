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
package com.coomia.erm.config.sns;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author spancer
 */
@Configuration
@ConfigurationProperties(prefix = "zsh.sns.alidayu")
public class AlidayuConfig {
    private static final String apiurl                    = "http://gw.api.taobao.com/router/rest";
    private static final String appkey                    = "";                                    // appkey
    private static final String secret                    = "";                                    // secret
    private static final String smsType                   = "normal";                              // 必填，短信类型
    private static final String smsFreeSignName           = "";                                    // 必填，短信签名
    private static final String smsVericationCodeTemplate = "";                                    // 必填，验证码短信模板ID

    public static final String getAppkey() {
        return appkey;
    }

    public static final String getSecret() {
        return secret;
    }

    public static String getApiurl() {
        return apiurl;
    }

    public static String getSmsType() {
        return smsType;
    }

    public static String getSmsFreeSignName() {
        return smsFreeSignName;
    }

    public static String getSmsVericationCodeTemplate() {
        return smsVericationCodeTemplate;
    }
}
