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
package com.coomia.erm.entity;

import java.util.List;

/**
 * @author spancer date: 2017年11月11日 下午1:53:22 <br/>
 */
public class ColumnHeader {

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
	 * 是否是下拉
	 */
	private boolean isSelect = false;
	/**
	 * 下拉列表内容
	 */
	private List<String> selectInfo;

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
	public ColumnHeader(String colCode, String colName, boolean orderAble) {
		super();
		this.colCode = colCode;
		this.colName = colName;
		this.orderAble = orderAble;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public List<String> getSelectInfo() {
		return selectInfo;
	}

	public void setSelectInfo(List<String> selectInfo) {
		this.selectInfo = selectInfo;
	}
	

}
