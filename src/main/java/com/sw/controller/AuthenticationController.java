package com.sw.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.beans.AppResult;
import com.sw.beans.ErrorEnum;
import com.sw.common.constants.AppConstants;
import com.sw.common.utils.IDUtils;
import com.sw.common.utils.TokenUtils;
import com.sw.common.utils.code.RedisValidateCodeRepository;
import com.sw.core.entity.AppUser;
import com.sw.core.service.IAppUserService;
import com.sw.enums.GeneralYesOrNoEnum;
import com.sw.enums.SexEnum;

/**
 * 登录注册
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/api/open")
public class AuthenticationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
	private RedisValidateCodeRepository redisValidateCodeRepository;//验证码存储

    @Autowired
    private IAppUserService appUserService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;


    /**
     * 账号登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/users/login/account")
    public AppResult accountLogin(String username,String password){
        if(StringUtils.isBlank(username)){
            return AppResult.build(ErrorEnum.UNAME_NULL);
        }
        if(StringUtils.isBlank(password)){
            return AppResult.build(ErrorEnum.PWD_NULL);
        }
        AppUser appUser = appUserService.findByUsername(username);
        if(appUser == null){
            return AppResult.build(ErrorEnum.UNAME_PWD_ERROR);
        }

        if(!StringUtils.equals(appUser.getPassword(),password)){
            return AppResult.build(ErrorEnum.UNAME_PWD_ERROR);
        }
        //生成token
        String token = TokenUtils.getToken();
        //user保存token
        appUser.setToken(token);
        appUserService.updateById(appUser);
        //redis 维护 token - user 关系
        redisTemplate.opsForHash().putAll(AppConstants.KEY_USER_INFO+token,buildRedisUserMap(appUser));
        //维护 uid - token关系，方便验证签名
        redisTemplate.opsForValue().set(String.valueOf(AppConstants.KEY_USER_INFO+appUser.getId()), token);
        //置空密码
        appUser.setPassword(null);
        return AppResult.ok(appUser);
    }
    
    /**
             *  短信验证码登录
     * @param code 验证码
     * @param mobile 手机号
     * @return
     */
    @PostMapping("/users/login/code")
    public AppResult codeLogin(HttpServletRequest request,String code,String mobile) {
    	//参数校验
    	if(StringUtils.isBlank(mobile)) {
    		return AppResult.build(ErrorEnum.MOBILE_IS_NULL);
    	}
    	if(StringUtils.isBlank(code)) {
    		return AppResult.build(ErrorEnum.SMS_CODE_NULL);
    	}
    	AppUser userByMobile = appUserService.findByMobile(mobile);
    	if(userByMobile == null) {
    		//手机号未注册
    		return AppResult.build(ErrorEnum.MOBILE_NO_REG);
    	}
    	
    	//ValidateCode validateCode = redisValidateCodeRepository.get(new ServletWebRequest(request));
//    	if(validateCode == null || validateCode.isExpired()) {
//    		//验证码失效
//    		return AppResult.build(ErrorEnum.CODE_EXPIRE);
//    	}
		/*
		 * if(!StringUtils.equals(validateCode.getCode(), code)){ //验证码错误 return
		 * AppResult.build(ErrorEnum.CODE_ERROR); }
		 */
    	String codeInRedis = (String)redisTemplate.opsForValue().get(AppConstants.KEY_SMS_CODE+mobile);
    	if(StringUtils.isBlank(codeInRedis)) {
    		//验证码失效
    		return AppResult.build(ErrorEnum.CODE_EXPIRE);
    	}
    	else {
    		//移除验证码
    		redisTemplate.delete(AppConstants.KEY_SMS_CODE+mobile);
    		//redisValidateCodeRepository.remove(new ServletWebRequest(request));
    		//生成token
            String token = TokenUtils.getToken();
            //user保存token
            userByMobile.setToken(token);
            appUserService.updateById(userByMobile);
            //redis 维护 token - user 关系
            redisTemplate.opsForHash().putAll(AppConstants.KEY_USER_INFO+token,buildRedisUserMap(userByMobile));
            //维护 uid - token关系，方便验证签名
            redisTemplate.opsForValue().set(String.valueOf(AppConstants.KEY_USER_INFO+userByMobile.getId()), token);
            //置空密码
            userByMobile.setPassword(null);
            return AppResult.ok(userByMobile);
    	}
    }
    

    /**
     * 构建redis中的用户新写
     * @param appUser
     * @return
     */
    private Map<String,Object> buildRedisUserMap(AppUser appUser){
        if(appUser == null) return null;
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("id",appUser.getId());
        userMap.put("username",appUser.getUsername());
        userMap.put("token",appUser.getToken());
        userMap.put("mobile",appUser.getMobile());
        userMap.put("email",appUser.getEmail());
        userMap.put("sex",appUser.getSex());
        userMap.put("nickname",appUser.getNickname());
        userMap.put("headImgUrl",appUser.getHeadImgUrl());
        userMap.put("province",appUser.getProvince());
        userMap.put("city",appUser.getCity());
        userMap.put("area",appUser.getArea());
        return userMap;
    }
    
    
    @RequestMapping("/users/logout")
    public AppResult logout(String uid) {

        String token = (String)redisTemplate.opsForValue().get(AppConstants.KEY_USER_INFO+uid);
        if(StringUtils.isNotBlank(token)){

            Boolean hasKey = redisTemplate.hasKey(AppConstants.KEY_USER_INFO+token);
            if(hasKey) {
                redisTemplate.delete(AppConstants.KEY_USER_INFO+token);
                LOGGER.info("[退出登录] 接收到 token : {} 退出登录 ");
            }
            redisTemplate.delete(AppConstants.KEY_USER_INFO+uid);
        }

    	return AppResult.ok();
    }

    @PostMapping("/reg/step1")
    public AppResult regstep1(String mobile,String code,String password) {
    	if(StringUtils.isBlank(mobile)) {
    		return AppResult.build(ErrorEnum.MOBILE_IS_NULL);
    	}
    	if(StringUtils.isBlank(code)) {
    		return AppResult.build(ErrorEnum.SMS_CODE_NULL);
    	}
    	if(StringUtils.isBlank(password)) {
    		return AppResult.build(ErrorEnum.PWD_NULL);
    	}
    	AppUser user = appUserService.mobileRegisterStep1(mobile, password, code);
    	return AppResult.ok(user);
    }
    

    
    @PostMapping("/users/register")
    public AppResult register(String mobile,String code,String password) {
    	if(StringUtils.isBlank(mobile)) {
    		return AppResult.build(ErrorEnum.MOBILE_IS_NULL);
    	}
    	if(StringUtils.isBlank(code)) {
    		return AppResult.build(ErrorEnum.SMS_CODE_NULL);
    	}
    	if(StringUtils.isBlank(password)) {
    		return AppResult.build(ErrorEnum.PWD_NULL);
    	}
    	//手机号是否已注册
    	AppUser user = appUserService.findByMobile(mobile);
    	if(user != null) {
    		return AppResult.build(ErrorEnum.MOBILE_IS_REGISTERED);
    	}
    	String codeInRedis = (String)redisTemplate.opsForValue().get(AppConstants.KEY_SMS_CODE+mobile);
    	if(StringUtils.isBlank(codeInRedis)) {
    		return AppResult.build(ErrorEnum.CODE_EXPIRE);
    	}
    	
    	if(!StringUtils.equals(code, codeInRedis)) {
    		return AppResult.build(ErrorEnum.SMS_CODE_ERROR);
    	}else {
    		//校验通过
    		user = new AppUser();
    		user.setId(IDUtils.genLongId());
    		user.setMobile(mobile);
    		user.setPassword(passwordEncoder.encode(password));
    		user.setIsBlacklist(GeneralYesOrNoEnum.ZERO.getCode());
    		user.setIsLocked(GeneralYesOrNoEnum.ZERO.getCode());
    		user.setIsDel(GeneralYesOrNoEnum.ZERO.getCode());
    		user.setCreateTime(new Date());
    		user.setSex(SexEnum.UNKNOWN.getCode());
    		user.setNickname("默认的昵称");
    		//生成token
            String token = TokenUtils.getToken();
            //user保存token
            user.setToken(token);
            appUserService.insert(user);
            //redis 维护 token - user 关系
            redisTemplate.opsForHash().putAll(AppConstants.KEY_USER_INFO+token,buildRedisUserMap(user));
            //维护 uid - token关系，方便验证签名
            redisTemplate.opsForValue().set(String.valueOf(AppConstants.KEY_USER_INFO+user.getId()), token);
            //置空密码
            user.setPassword(null);
            return AppResult.ok(user);
    	}
    }
}
