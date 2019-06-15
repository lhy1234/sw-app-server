package com.sw.beans.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 附近的人列表
 */
public class NearbyPeopleResp implements Serializable{

    private String uid;
    private String nickname;

    private String headImgUrl;
    private Integer sex;
   //ss private Integer
    private String distance;//距离


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
