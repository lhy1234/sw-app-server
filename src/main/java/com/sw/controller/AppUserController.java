package com.sw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.beans.AppResult;
import com.sw.core.entity.AppUser;
import com.sw.core.service.IAppUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-29
 */
@RestController
@RequestMapping("/api/open/users")
public class AppUserController {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	
	@Autowired
	private IAppUserService appUserService;
	
	@RequestMapping("/update/nickname")
	public AppResult updateNickname(String uid,String nickname) {
		
		AppUser user = appUserService.selectById(Long.valueOf(uid));
		user.setNickname(nickname);
		appUserService.updateById(user);
		return AppResult.ok();
	}
	
	
	@RequestMapping("/me")
	public AppResult me(String uid) {
		AppUser user = appUserService.selectById(Long.valueOf(uid));
		return AppResult.ok(user);
	}

}

