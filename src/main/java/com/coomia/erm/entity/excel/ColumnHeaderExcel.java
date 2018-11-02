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
package com.coomia.erm.entity.excel;

import org.jplus.hyberbin.excel.annotation.ExcelVoConfig;
import org.jplus.hyberbin.excel.bean.BaseExcelVo;

@ExcelVoConfig // Excel导出的配置
public class ColumnHeaderExcel extends BaseExcelVo {

	/**
	 * 字段码 如username
	 */
	private String colCode;

	/**
	 * 字段名称 如 姓名
	 */
	private String colName;

	/**
	 * 是否可排序
	 */
	private boolean orderAble = true;

	/**
	 * @return the colCode
	 */
	public String getColCode() {
		return colCode;
	}

	/**
	 * @param colCode
	 *            the colCode to set
	 */
	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * @param colName
	 *            the colName to set
	 */
	public void setColName(String colName) {
		this.colName = colName;
	}

	/**
	 * @return the orderAble
	 */
	public boolean isOrderAble() {
		return orderAble;
	}

	/**
	 * @param orderAble
	 *            the orderAble to set
	 */
	public void setOrderAble(boolean orderAble) {
		this.orderAble = orderAble;
	}

	/**
	 * @param colCode
	 * @param colName
	 * @param orderAble
	 */
	public ColumnHeaderExcel(String colCode, String colName, boolean orderAble) {
		super();
		this.colCode = colCode;
		this.colName = colName;
		this.orderAble = orderAble;
	}

	@Override
	public int getHashVal() {
		// TODO Auto-generated method stub
		return 0;
	}

}
