package com.sw.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Md5Util {
	
	public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
              // 生成一个MD5加密计算摘要  
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
    //主函数调用测试
    public static void main(String[] args) {
    	Long timestamp = System.currentTimeMillis();
    	System.out.println("签名时间戳："+System.currentTimeMillis());
    	
    	Map<String,String> paramMap = new TreeMap<>();
    	paramMap.put("uid", "123");
    	paramMap.put("token", "67848068-03a7-4f73-8833-54d15b3106e8");
    	paramMap.put("nickname", "美女");
    	paramMap.put("timestamp", String.valueOf(timestamp));

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) { 
        	String key = entry.getKey();
        	String value = entry.getValue();
        	sb.append(key+"="+value+"&");
        }
        String rs  = sb.substring(0, sb.length()-1).toString();
        System.out.println("签名："+md5(rs).toUpperCase());
        
//        
        
//        System.out.println(md5(str).toUpperCase());
    }



}
