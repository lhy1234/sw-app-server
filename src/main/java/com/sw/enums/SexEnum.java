package com.sw.enums;
/**
 * 性别枚举
 * @author Administrator
 *
 */
public enum SexEnum {
	
	UNKNOWN(0,"未知"),  MALE(1,"男"),  FEMALE(2,"女");
	
	
	private final int code;
	private final String value;
	
	
	
	
	public int getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
	private SexEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}
	
	

}
