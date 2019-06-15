package com.sw.core.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-29
 */
@TableName("sw_app_user")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    private String token;
    private String mobile;
    private String email;
    private Integer sex;
    /**
     * 昵称
     */
    private String nickname;
    @TableField("head_img_url")
    private String headImgUrl;
    /**
     * 所在省
     */
    private String province;
    /**
     * 所在市
     */
    private String city;
    /**
     * 所在县/区
     */
    private String area;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 上次修改密码时间
     */
    @TableField("last_update_pwd_time")
    private Date lastUpdatePwdTime;
    /**
     * 是否黑名单 0-否 1-是
     */
    @TableField("is_blacklist")
    private Integer isBlacklist;
    /**
     * 是否锁定  0-否 1-是
     */
    @TableField("is_locked")
    private Integer isLocked;
    /**
     * 是否删除 0-否 1-是
     */
    @TableField("is_del")
    private Integer isDel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastUpdatePwdTime() {
        return lastUpdatePwdTime;
    }

    public void setLastUpdatePwdTime(Date lastUpdatePwdTime) {
        this.lastUpdatePwdTime = lastUpdatePwdTime;
    }

    public Integer getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(Integer isBlacklist) {
        this.isBlacklist = isBlacklist;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "AppUser{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", token=" + token +
        ", mobile=" + mobile +
        ", email=" + email +
        ", sex=" + sex +
        ", nickname=" + nickname +
        ", headImgUrl=" + headImgUrl +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", lastUpdatePwdTime=" + lastUpdatePwdTime +
        ", isBlacklist=" + isBlacklist +
        ", isLocked=" + isLocked +
        ", isDel=" + isDel +
        "}";
    }
}
