package com.sw.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.beans.ErrorEnum;
import com.sw.common.constants.AppConstants;
import com.sw.common.utils.IDUtils;
import com.sw.common.utils.TokenUtils;
import com.sw.core.entity.AppUser;
import com.sw.core.exception.BusinessException;
import com.sw.core.mapper.AppUserMapper;
import com.sw.core.service.IAppUserService;
import com.sw.enums.GeneralYesOrNoEnum;
import com.sw.enums.SexEnum;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-29
 */
@Service
@Transactional
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public AppUser findByUsername(String username) {
    	if(StringUtils.isBlank(username)){
			return null;
		}
        return this.selectOne(new EntityWrapper<AppUser>()
        		.eq("username",username)
        		/*.eq("is_del", "0")*/);
    }

	@Override
	public AppUser findByMobile(String mobile) {
		if(StringUtils.isBlank(mobile)){
			return null;
		}
		return this.selectOne(new EntityWrapper<AppUser>()
				.eq("mobile", mobile)
				.eq("is_del", "0"));
	}

	
	
	@Override
	public AppUser smsCodeLogin(String code, String mobile) {
		
		return null;
	}

	
	
	@Override
	public AppUser mobileRegisterStep1(String mobile, String password, String code) {
		//校验手机号是否已注册
		AppUser user = this.findByMobile(mobile);
		if(user != null) {
			throw new BusinessException(ErrorEnum.MOBILE_NO_REG);
		}
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
        this.insert(user);
        //redis 维护 token - user 关系
        redisTemplate.opsForHash().putAll(AppConstants.KEY_USER_INFO+token,buildRedisUserMap(user));
        //维护 uid - token关系，方便验证签名
        redisTemplate.opsForValue().set(String.valueOf(AppConstants.KEY_USER_INFO+user.getId()), token);
        //置空密码
        user.setPassword(null);
		return null;
	}

	
	
	
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
		
    
    
}
