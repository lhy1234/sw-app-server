package com.sw.common.utils.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信验证码
 * ClassName: ValidateCode
 * @Description: 验证码
 * @author lihaoyang
 * @date 2018年3月1日
 */
public class ValidateCode implements Serializable {


    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1L;

    private String code;

    private LocalDateTime expireTime;//过期时间点

    
    
    public ValidateCode() {
		super();
	}


	/**
     *
     * <p>Description: </p>
     * @param code
     * @param expireTn 多少秒过期
     */
    public ValidateCode(String code, int expireTn) {
        this.code = code;
        //过期时间=当前时间+过期秒数
        this.expireTime = LocalDateTime.now().plusSeconds(expireTn);
    }


    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 验证码是否过期
     * @Description: 验证码是否过期
     * @param @return  true 过期，false 没过期
     * @return boolean   true 过期，false 没过期
     * @throws
     * @author lihaoyang
     * @date 2018年3月2日
     */
    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }



}