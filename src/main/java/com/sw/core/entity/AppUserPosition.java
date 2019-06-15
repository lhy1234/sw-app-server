package com.sw.core.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-25
 */
@TableName("sw_app_user_position")
public class AppUserPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("login_ip")
    private String loginIp;
    /**
     * 登录省份编码
     */
    @TableField("login_province_code")
    private String loginProvinceCode;
    private BigDecimal lon;
    private BigDecimal lat;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginProvinceCode() {
        return loginProvinceCode;
    }

    public void setLoginProvinceCode(String loginProvinceCode) {
        this.loginProvinceCode = loginProvinceCode;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AppUserPosition{" +
        "id=" + id +
        ", userId=" + userId +
        ", loginIp=" + loginIp +
        ", loginProvinceCode=" + loginProvinceCode +
        ", lon=" + lon +
        ", lat=" + lat +
        ", createTime=" + createTime +
        "}";
    }
}
