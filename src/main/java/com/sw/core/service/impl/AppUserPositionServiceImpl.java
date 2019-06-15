package com.sw.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.beans.dto.TaobaoIpResultDto;
import com.sw.beans.response.NearbyPeopleResp;
import com.sw.common.constants.AppConstants;
import com.sw.common.utils.JsonUtils;
import com.sw.common.utils.MathUtil;
import com.sw.core.entity.AppUser;
import com.sw.core.entity.AppUserPosition;
import com.sw.core.mapper.AppUserPositionMapper;
import com.sw.core.service.IAppUserService;
import com.sw.core.service.IAppUserPositionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * <p>

 *  服务实现类
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-25
 */
@Service
@Transactional
public class AppUserPositionServiceImpl extends ServiceImpl<AppUserPositionMapper, AppUserPosition> implements IAppUserPositionService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private IAppUserService appUserAccountService;


    @Override
    public void addClientPosition(Long userId, String ip, BigDecimal longitude, BigDecimal latitude) {
        //根据ip获取用户所在的省份
        Map<String,String> param = new HashMap<>();
        param.put("ip",ip);
       // String posInfo = HttpClientUtil.doGet("http://ip.taobao.com/service/getIpInfo.php",param);
        String posInfo = "";
        try{
            URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();

            urlCon.connect();

            InputStream inputStream = urlCon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String s = bufferedReader.readLine();
            System.out.println(s);
            posInfo = s;

        }catch (Exception e){

        }


        TaobaoIpResultDto taobaoIpResult = JsonUtils.jsonToPojo(posInfo,TaobaoIpResultDto.class);

        AppUserPosition position = new AppUserPosition();
        //position.setId();
        position.setUserId(userId);
        position.setLoginIp(ip);
        position.setLoginProvinceCode(taobaoIpResult.getData().getRegion_id());
        position.setLon(longitude);
        position.setLat(latitude);
        position.setCreateTime(new Date());
        this.insert(position);



        //将位置信息放入缓存
        String redisKey = AppConstants.KEY_GEO_PREFIX+position.getLoginProvinceCode();
        Point point = new Point(longitude.doubleValue(),latitude.doubleValue());
        redisTemplate.opsForGeo().geoAdd(redisKey,point,String.valueOf(userId));
    }


    @Override
    public List<NearbyPeopleResp> nearbyPeople(Long userId) {
        AppUserPosition userPos = findByUserId(userId);
        String redisKey = AppConstants.KEY_GEO_PREFIX+userPos.getLoginProvinceCode();
        //半径
        Circle circle = new Circle(userPos.getLon().doubleValue(),userPos.getLat().doubleValue(), Metrics.KILOMETERS.getMultiplier());
        //命令行参数
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance() //距离
                .includeCoordinates() //??不知道啥意思
                .sortAscending() //按距离排序
                .limit(100000);//查前几条，就是分页，这里没必要分页，一下查出来也没多少，让前端去分页
        GeoResults<RedisGeoCommands.GeoLocation<Object>>  results = redisTemplate.opsForGeo().geoRadius(redisKey,circle,args);

        //返回是这样式的数据：：RedisGeoCommands.GeoLocation(name=1005, point=Point [x=116.319522, y=40.148395])
        System.out.println(results);
        List<NearbyPeopleResp> nearbyPeopleList = new ArrayList<>();
        NearbyPeopleResp resp = null;
        for(GeoResult<RedisGeoCommands.GeoLocation<Object>> obj:results){
            System.out.println(obj.getContent());

            Long nearbyUserId = Long.valueOf((String)obj.getContent().getName());//userId
            double dist  = obj.getDistance().getValue();//距离
            //查附近的人信息
            AppUser nearbyUser = appUserAccountService.selectById(nearbyUserId);
            resp = new NearbyPeopleResp();
            resp.setUid(String.valueOf(nearbyUser.getId()));
            resp.setHeadImgUrl(nearbyUser.getHeadImgUrl());
            resp.setNickname(nearbyUser.getNickname());
            resp.setSex(nearbyUser.getSex());
            resp.setDistance(String.valueOf(MathUtil.roundDouble(dist,2)));
            nearbyPeopleList.add(resp);
        }
        return nearbyPeopleList;
    }

    @Override
    public AppUserPosition findByUserId(Long userId) {
        return  this.selectOne(new EntityWrapper<AppUserPosition>().eq("user_id",userId));
    }
}
