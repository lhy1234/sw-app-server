package com.sw.core.service.impl;

import com.sw.core.entity.Demo;
import com.sw.core.mapper.DemoMapper;
import com.sw.core.service.IDemoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-29
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

	//不用再进行mapper的注入.
	
	/**
	 * DemoServiceImpl  继承了IDemoService
	 * 1. 在DemoServiceImpl中已经完成Mapper对象的注入,直接在DemoServiceImpl中进行使用
	 * 2. 在DemoServiceImpl中也帮我们提供了常用的CRUD方法， 基本的一些CRUD方法在Service中不需要我们自己定义.
	 * 
	 * 
	 */
}
