package com.sw.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.beans.AppResult;
import com.sw.beans.ErrorEnum;
import com.sw.common.constants.AppConstants;
import com.sw.common.utils.SignUtil;

public class SignHandlerInterceptor implements HandlerInterceptor{

	private static final Logger LOGGER = LoggerFactory.getLogger(SignHandlerInterceptor.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//获取请求的参数集合
		Map<String,String> paramsMap = getRequestParamMap(request);
		//请求url 
		String url = request.getRequestURL().toString();
		System.out.println("url: "+url);
		String x_sign = request.getHeader("x_sign");
		String uid = paramsMap.get("uid");
		String timestamp = paramsMap.get("timestamp");
	

		LOGGER.info("[验签拦截器] 请求: {} 获取到uid: {} , 签名x_sign : {},时间戳：{}",url,uid,x_sign,timestamp);

		//签名为空
		if(StringUtils.isBlank(x_sign)) {
			LOGGER.info("[验签拦截器]  <未获取>  到签名, x_sign : {} ", x_sign);
			response.getWriter().write(objectMapper.writeValueAsString(AppResult.build(ErrorEnum.SIGN_NULL)));
			return false;
		}
		//uid为空
		if(StringUtils.isBlank(uid)) {
			LOGGER.info("[验签拦截器]  <未获取>  到uid ,   uid : {} ", uid);
			response.getWriter().write(objectMapper.writeValueAsString(AppResult.build(ErrorEnum.UID_NULL)));
			return false;
		}
		//时间戳为空
		if(StringUtils.isBlank(timestamp)) {
			LOGGER.info("[验签拦截器]  <未获取>  到timestamp ,   timestamp : {} ", timestamp);
			response.getWriter().write(objectMapper.writeValueAsString(AppResult.build(ErrorEnum.TIMESTAMP_NULL)));
			return false;
		}
		Long timestampReq = Long.valueOf(timestamp);//请求的中的时间戳
		//判断时间戳是否超时
		Long currentMillis = System.currentTimeMillis();//当前毫秒数
		if((currentMillis-timestampReq)>60000) {
			LOGGER.info("[验签拦截器]  时间戳超时 , 相差毫秒数:{} {}", (currentMillis-timestampReq),"ms" );
			response.getWriter().write(objectMapper.writeValueAsString(AppResult.build(ErrorEnum.TIMESTAMP_TIMEOUT)));
			return false;
		}
		
		
		//根据uid获取token
		String token = (String)redisTemplate.opsForValue().get(AppConstants.KEY_USER_INFO+uid);
		if(StringUtils.isBlank(token)) {
			LOGGER.info("[验签拦截器] 根据uid未获取到redis中的token : {} ", token);
			response.getWriter().write(objectMapper.writeValueAsString(AppResult.build(ErrorEnum.NOT_LOGIN)));
			return false;
		}
		//将token放入参数map进行签名计算
		paramsMap.put("token", token);
		//计算签名
		String calSign = SignUtil.createSign(url,paramsMap,false);
		if(!StringUtils.equals(x_sign,calSign)) { //签名失败
		  LOGGER.info("[验签拦截器] MD5 验签失败，参数 x_sign ：{} ,后台生成的签名：{}",x_sign,calSign);
		  response.getWriter().write(objectMapper.writeValueAsString(AppResult.build(
		  ErrorEnum.SIGN_ERROR))); return false;
		}
		
		LOGGER.info("[验签拦截器] 请求: {} 验签成功 >>>>>> 获取到uid: {} , 签名x_sign :{} ",url,uid,x_sign);
		return true;
	}
	
	
	/**
	 * 获取request参数集合
	 * @param request
	 * @return
	 */
	private Map<String,String> getRequestParamMap(final HttpServletRequest request) {
		Map<String,String> paramMap = new HashMap<>();
		if(request != null) {
			Enumeration<String> enu = request.getParameterNames();  
			while(enu.hasMoreElements()){  
				String paramName= enu.nextElement();  
				String value = request.getParameter(paramName);
				paramMap.put(paramName, value);
			}
		}
		return paramMap;
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
