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
package com.coomia.erm.service;

import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ErmSmsEntity;

/**
 * @author spancer date: 2017年11月9日 上午8:49:08 <br/>
 */
public interface ErmSmsService {

	/**
	 * sendMsg TODO
	 * 
	 * @param telphone
	 * @param name
	 * @param auditStatus
	 */
	boolean sendMsg(String telphone, String name, int auditStatus, boolean isTest);

	ErmSmsEntity queryObject(Integer id);

	List<ErmSmsEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(ErmSmsEntity ermSms);

	void update(ErmSmsEntity ermSms);

	void delete(Integer id);

	void deleteBatch(Integer[] ids);
	/**
	 * 验证验证码是否存在
	 * @param telephone
	 * @param code
	 * @return
	 */
	ErmSmsEntity queryObjectByCode(String telephone, String code);
}
