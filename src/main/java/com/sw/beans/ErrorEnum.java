package com.sw.beans;

public enum ErrorEnum {
	
	
    FAILURE(0, "失败" ),
    SUCCESS(1, "成功"),
    
    SYS_EXCEPTION(444444,"系统异常"),
    
    /*************** 1 ~ 10 ，签名参数 *************************/ 

    NO_TOKEN(2,"token为空"),
    INVALID_TOKEN(3,"token无效"),
    
    
    
    SIGN_NULL(4,"签名为空"),
    SIGN_ERROR(5,"签名失败"),
    
    UID_NULL(6,"uid为空"),
    TIMESTAMP_NULL(7,"timestamp null"),
    TIMESTAMP_TIMEOUT(8,"timeout"),
    
    
    DATA_VALIDATE_EX(11,"数据校验异常"),

    MISSING_PAGE_INFO(12,"缺少分页参数"),

    
    

    // 状态码由6位数字组成
    //                 1-2位表示系统   3-4位表示功能类别  5-6位表示具体功能
    // 1010100 登录相关
    NOT_LOGIN(1010100,"未登录"),
    UNAME_PWD_ERROR(1010101,"用户名或密码错误"),
    MOBILE_IS_NULL(1010102,"手机号为空"),
    MOBILE_NO_REG(1010103,"手机号未注册"),
    CODE_EXPIRE(1010104,"验证码已失效"),
    CODE_ERROR(1010105,"验证码错误"),
    
    //1010200  注册相关
   
    SMS_CODE_ERROR(1010200,"验证码错误"),
    SMS_CODE_INVALID(1010201,"验证码已失效"),
    SMS_CODE_NULL(1010202,"验证码为空"),
    MOBILE_IS_REGISTERED(1010103,"该手机号已注册"),
    
    
    
    

    UNAME_NULL(1008,"用户名为空"),

    PWD_NULL(1009,"密码为空"),

    ;


    private final int status;
    private final String msg;

    ErrorEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int status() {
        return status;
    }



    public String msg() {
        return msg;
    }


}
