package com.sw.beans.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 调用淘宝根据ip查询地址信息接口返回对象
 */
public class TaobaoIpResultDto implements Serializable {

  /*  {
        "code":0,
            "data":{
                "ip":"",
                "country":"",
                "area":"",
                "region":"",
                "city":"",
                "county":"",
                "isp":"",
                "country_id":"",
                "area_id":"",
                "region_id":"",
                "city_id":"",
                "county_id":"",
                "isp_id":""
    }
    }*/

    private Integer code;

    private TaobaoIpDto data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public TaobaoIpDto getData() {
        return data;
    }

    public void setData(TaobaoIpDto data) {
        this.data = data;
    }
}
