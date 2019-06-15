package com.sw.controller;



import com.sw.beans.AppResult;
import com.sw.beans.response.NearbyPeopleResp;
import com.sw.common.utils.IpUtils;

import com.sw.core.service.IAppUserPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-25
 */
@RestController
@RequestMapping("/api/open/users/position")
public class AppUserPositionController {



    @Autowired
    private IAppUserPositionService appUserPositionService;

    /**
     * 客户端上送位置信息
     */
    @PostMapping("/loc")
    public AppResult clientLocation(HttpServletRequest request,String uid, String longitude, String latitude){

        String ip = IpUtils.getIpAddr(request);
        ip = "111.196.244.208";
        //判断ip所在省份
        appUserPositionService.addClientPosition(Long.valueOf(uid),ip,new BigDecimal(longitude),new BigDecimal(latitude));
        System.out.println(uid+" , "+ longitude+" ,  "+ latitude);
        System.out.println(ip);

        return AppResult.ok();
    }


    @GetMapping("/nearby")
    public AppResult nearbyPeople(String uid){
        List<NearbyPeopleResp> list = appUserPositionService.nearbyPeople(1001L);
        return AppResult.ok(list);
    }
}

