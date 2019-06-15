package com.sw.core.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sw.core.service.SmsCodeSender;

/**
 * 默认的短信验证码发送类
 * ClassName: DefaultSmsCodeSender
 * @Description: TODO
 * @author lihaoyang
 * @date 2018年3月7日
 */
@Service
public class DefaultSmsCodeSender implements SmsCodeSender{

    @Override
    public void send(String mobile, String code) {
        //存数据库
        System.err.println("向手机 :"+mobile+" 发送短信验证码 :"+code);
    }

}