package com.sw.common.utils.code;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成类 ClassName: ImageCodeGenerator
 * 
 * @Description: TODO
 * @author lihaoyang
 * @date 2018年3月2日
 */
@Component("smsCodeGenerator")
public class SmsCodeGenerator /* implements ValidateCodeGenerator */ {

	// @Autowired
	// private SecurityProperties securityProperties;
	
	private static final int CODE_LENGTH = 6; //验证码长度
	private static final int EXPIRE_IN = 60*10;

	//@Override
	public ValidateCode generator(ServletWebRequest request) {
		// 生成验证码，长度从配置读取
		String code = RandomStringUtils.randomNumeric(SmsCodeGenerator.CODE_LENGTH);
		return new ValidateCode(code, SmsCodeGenerator.EXPIRE_IN);
	}

	/*
	 * public SecurityProperties getSecurityProperties() { return
	 * securityProperties; }
	 * 
	 * public void setSecurityProperties(SecurityProperties securityProperties) {
	 * this.securityProperties = securityProperties; }
	 */

}