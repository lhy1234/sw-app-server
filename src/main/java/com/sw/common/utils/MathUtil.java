package com.sw.common.utils;

import java.math.BigDecimal;
public class MathUtil {



    /**
     * 对double保留n位小数
     * @param d
     * @return
     */
    public static Double roundDouble(Double d,int n){
        BigDecimal   b   =   new BigDecimal(d);
        Double   f1   =   b.setScale(n,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }



    public static void main(String[] args) {
        double d = MathUtil.roundDouble(1.235567788,2);
        System.out.println(d);
    }
}
