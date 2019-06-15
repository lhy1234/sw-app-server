package com.sw.core.service;

import com.sw.core.entity.AppUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-29
 */
public interface IAppUserService extends IService<AppUser> {

	/**
	 * 通过用户名查询
	 * @param username 
	 * @return
	 */
    AppUser findByUsername(String username);

    /**
     * 通过手机号查询
     * @param mobile 手机号
     * @return
     */
	AppUser findByMobile(String mobile);

	
	/**
	   * 短信验证码登录
	 * @param code 验证码
	 * @param mobile 手机号
	 * @return
	 */
	AppUser smsCodeLogin(String code, String mobile);
	
	/**
	 * 手机号注册，第一步
	 * @param mobile 手机号
	 * @param password 密码
	 * @param code 验证码
	 * @return
	 */
	AppUser mobileRegisterStep1(String mobile,String password,String code);

}
