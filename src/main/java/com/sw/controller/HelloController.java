package com.sw.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.beans.AppResult;

@RestController
public class HelloController {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String hello(){
    	
   
        return "hello!";
    }
    
   
    
    @GetMapping("/log/info")
    public AppResult info() {
    	LOGGER.info("【测试日志】 测试info级别的日志， 测试info级别的日志");
    	LOGGER.info("【测试日志】 测试info级别的日志， 测试info级别的日志");
    	LOGGER.info("【测试日志】 测试info级别的日志， 测试info级别的日志");
    	int k = 0;
    	while(k < 10) {
    		k++;
    		LOGGER.info("【测试日志】 测试info级别的日志， 测试info级别的日志");
    	}
    	return AppResult.ok();
    }
    
    @GetMapping("/log/warn")
    public AppResult warn() {
    	int k = 0;
    	while(k < 10) {
    		k++;
    		LOGGER.warn("【测试日志】 测试warn级别的日志， 测试warn级别的日志");
    	}
    	return AppResult.ok();
    }
    
    @GetMapping("/log/error")
    public AppResult error() {
    	int k = 0;
    	while(k < 10) {
    		k++;
    		LOGGER.error("【测试日志】 测试error级别的日志， 测试error级别的日志");
    	}
    	return AppResult.ok();
    }
  
}
