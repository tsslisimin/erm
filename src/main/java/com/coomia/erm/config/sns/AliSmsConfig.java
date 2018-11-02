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

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @author spancer date: 2017年11月20日 下午2:22:16 <br/>
 */

public class AliSmsConfig {
  private AliSmsConfig() {};

  public static String SIGN_NAME = "慈利县教育局";//TODO 签名
  public static String AUDIT_SUCCESS = "template-01";// TODO 审核通过的模板
  public static String AUDIT_FAIL = "template-02";// TODO 审核不通过模板
  public static String AUDIT_OFFERING = "template-03";// TODO 放款中模板
  static final String product = "Dysmsapi";
  static final String domain = "dysmsapi.aliyuncs.com";
  static String accessKeyId = "apikey";// TODO 此处私钥 填写自己的
  static String accessKeySecret = "apikeysecret";// TODO 此处私钥 填写自己的
  static final IClientProfile profile =
      DefaultProfile.getProfile("cn-changsha", accessKeyId, accessKeySecret);
  static {
    try {
      System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
      System.setProperty("sun.net.client.defaultReadTimeout", "10000");
      DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    } catch (ClientException e) {
      e.printStackTrace();
    }
  }

  /**
   * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
   */
  private static class SingletonHolder {
    /**
     * 静态初始化器，由JVM来保证线程安全
     */
    private static IAcsClient acsClient = new DefaultAcsClient(profile);
  }

  public static IAcsClient getAcsClient() {
    return SingletonHolder.acsClient;
  }

  
}
