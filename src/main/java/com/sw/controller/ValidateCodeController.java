package com.sw.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;


import com.sw.beans.AppResult;
import com.sw.common.constants.AppConstants;
import com.sw.common.utils.JsonUtils;
import com.sw.common.utils.code.RedisValidateCodeRepository;
import com.sw.common.utils.code.SmsCodeGenerator;
import com.sw.common.utils.code.ValidateCode;
import com.sw.core.service.SmsCodeSender;


/**
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/sw/app/code")
public class ValidateCodeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateCodeController.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private SmsCodeSender smsCodeSender;//短信验证码发送
	
	@Autowired
	private SmsCodeGenerator smsCodeGenerator;//验证码生成
	
	@Autowired
	private RedisValidateCodeRepository redisValidateCodeRepository;//验证码存储
	
	@RequestMapping("/sms/send")
	public AppResult smsSend(HttpServletRequest request) throws ServletRequestBindingException  {
		//验证码生成接口生成验证码
        ValidateCode smsCode = smsCodeGenerator.generator(new ServletWebRequest(request));
        //调用验证码存储接口存验证码
        //redisValidateCodeRepository.save(new ServletWebRequest(request) , smsCode);
        //获取手机号
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        //发送短信验证码
        redisTemplate.opsForValue().set(AppConstants.KEY_SMS_CODE+mobile, smsCode.getCode(),10,TimeUnit.MINUTES);
        smsCodeSender.send(mobile, smsCode.getCode());
        return AppResult.ok();
	}
}
