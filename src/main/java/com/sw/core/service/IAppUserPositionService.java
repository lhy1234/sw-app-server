package com.sw.core.service;

import com.sw.beans.response.NearbyPeopleResp;
import com.sw.core.entity.AppUserPosition;
import com.baomidou.mybatisplus.service.IService;


import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-25
 */
public interface IAppUserPositionService extends IService<AppUserPosition> {

    /**
     * 添加客户端的位置信息
     * @param userId
     * @param ip
     * @param longitude
     * @param latitude
     */
    void addClientPosition(Long userId, String ip, BigDecimal longitude, BigDecimal latitude);

    /**
     * 附近的人列表
     * @param userId
     * @return
     */
    List<NearbyPeopleResp> nearbyPeople(Long userId);

    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    AppUserPosition findByUserId(Long userId);
}
