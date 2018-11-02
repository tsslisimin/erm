package com.coomia.erm.entity;

import java.io.Serializable;

public class ErmExportCloumn implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -627316915506058737L;

	private String key;
	
	private String value;
	
	private boolean check;
	
	public ErmExportCloumn() {
		
	}
	
	public ErmExportCloumn(String key,String value,boolean check) {
		this.key = key;
		this.value = value;
		this.check = check;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	
	
	
}
