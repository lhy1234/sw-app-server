package com.sw.generator;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MybatisPlusGenerator {

    public static void main(String[] args) {
       

        // 1.全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("E:/sw-app-code");//生成文件的输出目录
        gc.setFileOverride(true);//是否覆盖已有文件
        gc.setActiveRecord(false);//开启 ActiveRecord 模式
        gc.setEnableCache(false);// 是否在xml中添加二级缓存配置
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        //gc.setServiceName("%sService");  // 设置生成的service接口的名字的首字母是否为I
        gc.setAuthor("lihaoyang");
      
        

        // 2.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://127.0.0.1/db_sw?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true");
       

        // 3.策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        //strategy.setDbColumnUnderline(true);  // 指定表名 字段名是否使用下划线
        strategy.setTablePrefix(new String[]{"sw_"});// 此处可以修改为您的表前缀 .setTablePrefix("tbl_")
        strategy.setNaming(NamingStrategy.underline_to_camel);// 数据库表映射到实体的命名策略
        //strategy.setInclude("tbl_employee");  // 生成的表
       

        // 4.包名策略配置 
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);//
        pc.setController("com.sw.controller");
        pc.setEntity("com.sw.core.entity");
        pc.setMapper("com.sw.core.mapper");
        pc.setService("com.sw.core.service");
        pc.setServiceImpl("com.sw.core.service.impl");
        pc.setXml("com.sw.core.mapper");
        
      //5. 整合配置
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc);
        mpg.setStrategy(strategy);
        mpg.setDataSource(dsc);
        mpg.setPackageInfo(pc);

        //6.执行
        mpg.execute();
        
        
    }
}
