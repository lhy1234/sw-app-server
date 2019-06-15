package com.sw.core.exception;

import com.sw.beans.ErrorEnum;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException{
	
	/**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1L;
	
	private Integer status;
	
	private String msg;
	
	public BusinessException(ErrorEnum errorEnum) {
		this.status = errorEnum.status();
		this.msg = errorEnum.msg();
	}

	public BusinessException(Integer status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
	
	
	
	
	
	

	
}