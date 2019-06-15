package com.sw.common.utils;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MybatisPlusGenerator {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("E:/sw-app-code");//生成文件的输出目录
        gc.setFileOverride(true);//是否覆盖已有文件
        gc.setActiveRecord(false);//开启 ActiveRecord 模式
        gc.setEnableCache(false);// 是否在xml中添加二级缓存配置
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("lihaoyang");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://127.0.0.1/db_sw?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[]{"sw_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);//
        pc.setController("com.sw.controller");
        pc.setEntity("com.sw.core.entity");
        pc.setMapper("com.sw.core.mapper");
        pc.setService("com.sw.core.service");
        pc.setServiceImpl("com.sw.core.service.impl");
        pc.setXml("com.sw.core.mapper");
        mpg.setPackageInfo(pc);

        mpg.execute();
    }
}
