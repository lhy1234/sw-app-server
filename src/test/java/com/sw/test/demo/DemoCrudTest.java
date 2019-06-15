package com.sw.test.demo;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.core.entity.Demo;
import com.sw.core.mapper.DemoMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoCrudTest {

	@Autowired
	private DemoMapper demoMapper;

	

	/**
	 * 条件构造器 删除操作
	 */
	@Test
	public void testEntityWrapperDelete() {
		demoMapper.delete(new EntityWrapper<Demo>().eq("name", "李四"));
	}

	/**
	 * 条件构造器 修改操作
	 */
	@Test
	public void testEntityWrapperUpdate() {

		Demo demo = new Demo();
		demo.setAge(10);
		demo.setName("我是谁？");

		demoMapper.update(demo, new EntityWrapper<Demo>().eq("id", "4"));
	}

	/**
	 * 条件构造器    查询操作,分页查询
	 */
	@Test
	public void testEntityWrapperSelect() {
		// 我们需要分页查询demo表中，年龄在18~22之间且性别为男de所有用户

//		List<Demo> demos = demoMapper.selectPage(new Page<Demo>(1, 1),
//				new EntityWrapper<Demo>()
//				.between("age", 18, 22)
//				.eq("sex", 1)
//			);
	
		// System.err.println(demos);

		
//		@SuppressWarnings("unchecked")
//		List<Demo> emps = demoMapper.selectPage(
//				new Page<Demo>(2,1), 
//				Condition.create()
//				.between("age", 18, 22).eq("sex", 1)
//				);
//		System.err.println(emps);		

		 

		// 查询demo表中， 性别为女2 并且名字中带有"张" 或者 邮箱中带有"a"

//		List<Demo> demos = demoMapper.selectList(
//				new EntityWrapper<Demo>()
//				.eq("sex", 2)
//				//.like("name", "张")
//				.or()    // SQL: (gender = ? AND last_name LIKE ? OR email LIKE ?)    
//				//.orNew()   // SQL: (gender = ? AND last_name LIKE ?) OR (email LIKE ?) 
//				.like("name", "张")
//				);
//		System.err.println(demos);

		// 查询性别为女的, 根据age进行排序(asc/desc), 简单分页

		List<Demo> emps  = demoMapper.selectList(
				new EntityWrapper<Demo>()
				.eq("sex", 1)
				//.orderBy("age")
				.orderBy("age", false)
				//.orderDesc(Arrays.asList(new String [] {"age"}))
				//.last("desc limit 1,3")
				);
		System.err.println(emps);

	}
	
	
	
	/**
	 * 通用 删除操作
	 */
	@Test
	public void testCommonDelete() {
		//1 .根据id进行删除
//		Integer result = demoMapper.deleteById(13);
//		System.out.println("result: " + result );
		//2. 根据 条件进行删除
		Map<String,Object> columnMap = new HashMap<>();
		columnMap.put("name", "有名字啦");
		Integer result = demoMapper.deleteByMap(columnMap);
		System.err.println("result: " + result );
		
		//3. 批量删除
//		List<Integer> idList = new ArrayList<>();
//		idList.add(3);
//		idList.add(4);
//		idList.add(5);
//		Integer result = employeeMapper.deleteBatchIds(idList);
//		System.out.println("result: " + result );
	}
	
	
	
	
	/**
	 * 通用 查询操作
	 */
	@Test
	public void  testCommonSelect() {
		//1. 通过id查询
//		Demo demo = demoMapper.selectById(1);
//		System.err.println(demo);
		
		//2. 通过多个列进行查询    id  +  lastName
//		Demo  demo = new Demo();
//		demo.setName("张三");
//		
//		Demo result = demoMapper.selectOne(demo);
//		System.err.println("result: " +result );
//		
		
		//3. 通过多个id进行查询    <foreach>
//		List<Integer> idList = new ArrayList<>();
//		idList.add(4);
//		idList.add(5);
//		idList.add(6);
//		idList.add(7);
//		List<Demo> demos = demoMapper.selectBatchIds(idList);
//		System.err.println(demos);
		
		//4. 通过Map封装条件查询
//		Map<String,Object> columnMap = new HashMap<>();
//		columnMap.put("name", "张4");
//		
//		List<Demo> demos2 = demoMapper.selectByMap(columnMap);
//		System.err.println(demos2);
		
		//5. 分页查询
		List<Demo> demoss = demoMapper.selectPage(new Page<Demo>(1, 2), null);
		System.err.println(demoss);
	}
	
	
	

	/**
	 * 通用 更新操作
	 */
	@Test
	public void testCommonUpdate() {
		// 初始化修改对象
		Demo demo = new Demo();
		demo.setId(5L);
		demo.setName("有名字啦");

		// 不为空的才修改
		// Integer result = demoMapper.updateById(demo);
		// 会把其他没设置的字段设为空
		Integer result = demoMapper.updateAllColumnById(demo);

		System.out.println("result: " + result);
	}
	
	/**
	 * 通用 插入炒作
	 */
	@Test
	public void testAdd() {

		Demo demo = new Demo();
		// demo.setId(1);
		demo.setAge(28);
		demo.setCreateTime(new Date());
		demo.setName("测试ID");
		demo.setSex(1);

		// insert方法在插入时， 会根据实体类的每个属性进行非空判断，只有非空的属性对应的字段才会出现到SQL语句中
		// int rs = demoMapper.insert(demo);

		int rs = demoMapper.insertAllColumn(demo);
		assertEquals(1, rs);

		// 获取当前数据在数据库中的主键值
		Long key = demo.getId();
		System.err.println("key:" + key);
	}

}
