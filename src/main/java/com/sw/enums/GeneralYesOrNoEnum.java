package com.sw.enums;
/**
 * 通用的是否
 * @author Administrator
 *
 */
public enum GeneralYesOrNoEnum {
	
	ZERO(0,"否"), ONE(1,"是") ;
	
	
	private final int code;
	private final String value;
	
	GeneralYesOrNoEnum(int code,String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
	
	
}
