package com.sw.common.utils.code;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.sw.common.constants.AppConstants;
import com.sw.common.utils.JsonUtils;
import com.sw.core.exception.BusinessException;

/**
 * redis验证码存取策略 ClassName: RedisValidateCodeRepository
 * 
 * @Description: redis验证码存取策略
 * @author lihaoyang
 * @date 2018年3月14日
 */
@Component("redisValidateCodeRepository")
public class RedisValidateCodeRepository /* implements ValidateCodeRepository */ {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// @Override
	public void save(ServletWebRequest request, ValidateCode code) {
		String key = buildKey(request);
		redisTemplate.opsForValue().set(key, code, 10, TimeUnit.MINUTES);
		logger.info("【验证码redis存储实现】redis存进去了一个新的key：{}，值:{}", key, JsonUtils.objectToJson(code));
	}

	//@Override
	public ValidateCode get(ServletWebRequest request) {
		Object value = redisTemplate.opsForValue().get(buildKey(request));
		if (value == null) {
			return null;
		}
		return (ValidateCode) value;
	}

	// @Override
	public void remove(ServletWebRequest request) {
		String key = buildKey(request);
		logger.info("【验证码redis存储实现】redis删除了一个key：{}", key);
		redisTemplate.delete(key);
	}

	/**
	 * 构建验证码在redis中的key
	 * 
	 * @Description: 构建验证码在redis中的key
	 * @param @return
	 * @return String 验证码在redis中的key
	 * @throws @author lihaoyang
	 * @date 2018年3月14日
	 */
	private String buildKey(ServletWebRequest request) {
		// 获取设备id
		// String deviceId = request.getHeader("deviceId");
		String mobile = request.getParameter("mobile");

		if (StringUtils.isBlank(mobile)) {
			throw new BusinessException(4444,"【验证码redis存储实现】mobile为空，未携带mobile参数");
		}

		return AppConstants.KEY_SMS_CODE + mobile;
	}

}