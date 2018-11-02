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
package com.coomia.erm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.coomia.erm.config.sns.AliSmsConfig;
import com.coomia.erm.dao.ErmSmsDao;
import com.coomia.erm.entity.ErmSmsEntity;
import com.coomia.erm.service.ErmSmsService;
import com.google.gson.JsonObject;

/**
 * @author spancer date: 2017年11月9日 上午9:09:58 <br/>
 */
@Service("ermSmsService")
public class ErmSmsServiceImpl implements ErmSmsService {
	private static final Logger LOG = LogManager.getLogger(ErmSmsServiceImpl.class.getName());

	@Autowired
	private ErmSmsDao ermSmsDao;

	@Override
	public ErmSmsEntity queryObject(Integer id) {
		return ermSmsDao.queryObject(id);
	}

	@Override
	public List<ErmSmsEntity> queryList(Map<String, Object> map) {
		return ermSmsDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return ermSmsDao.queryTotal(map);
	}

	@Override
	public void save(ErmSmsEntity ermSms) {
		ermSms.setCreateDate(new Date());
		ermSmsDao.save(ermSms);
	}

	@Override
	public void update(ErmSmsEntity ermSms) {
		ermSms.setUpdateDate(new Date());
		ermSmsDao.update(ermSms);
	}

	@Override
	public void delete(Integer id) {
		ermSmsDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		ermSmsDao.deleteBatch(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.coomia.erm.service.ErmSmsService#sendMsg(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public boolean sendMsg(String telphone, String name, int auditStatus, boolean isTest) {
	    if(isTest)
	      return true;
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(telphone);
		// 尊敬的${username}，您的资助申请已经审批通过，请耐心等待放款！
		JsonObject params = new JsonObject();
		if (null == name || name.isEmpty())
			name = telphone;
		params.addProperty("username", name);
		request.setTemplateParam(params.toString());

		SendSmsResponse response = null;
		try {
			IAcsClient acsClient = AliSmsConfig.getAcsClient();
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(AliSmsConfig.SIGN_NAME);
			// 必填:短信模板-可在短信控制台中找到
			if (auditStatus == 0)// 审核不通过
				request.setTemplateCode(AliSmsConfig.AUDIT_FAIL);
			else if (auditStatus == 1)// 审核通过
				request.setTemplateCode(AliSmsConfig.AUDIT_SUCCESS);
			else if (auditStatus == 2) // 已放款
				request.setTemplateCode(AliSmsConfig.AUDIT_OFFERING);
			response = acsClient.getAcsResponse(request);
			if ("OK".equals(response.getCode())) {
				System.out.println("Code=" + response.getCode());
				System.out.println("Message=" + response.getMessage());
				System.out.println("RequestId=" + response.getRequestId());
				System.out.println("BizId=" + response.getBizId());
				return true;
			}
		} catch (Exception e) {
			LOG.error("短信发送异常:" + request.getPhoneNumbers(), e);
			return false;
		}
		return false;
	}

	@Override
	public ErmSmsEntity queryObjectByCode(String telephone, String code) {
		
		return this.ermSmsDao.queryObjectByCode(telephone,code);
	}

}
