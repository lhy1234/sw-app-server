package com.sw.common.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class TokenUtils {


   /* public static String getToken(Long uid){
    	if(uid == null) return null;
        return Md5Util.md5(String.valueOf(uid));
    }*/

    public static String getToken(){

        return UUID.randomUUID().toString();
    }
}
