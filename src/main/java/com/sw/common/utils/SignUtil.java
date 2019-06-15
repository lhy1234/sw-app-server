package com.sw.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SignUtil {

	/**
	 *  生成签名时, 参数不要使用 'urlencode'. 在调用 api 时, 才需要对参数做 'urlencode'
	 * @param url
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(String url,Map<String, String> params , boolean encode ) throws UnsupportedEncodingException {
		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {
			if (first) {
				first = false;
			} else {
				temp.append("&");
			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueString = "";
			if (null != value) {
				valueString = String.valueOf(value);
			}
			if (encode) {
				temp.append(URLEncoder.encode(valueString, "UTF-8"));
			} else {
				temp.append(valueString);
			}
		}
		String needSign = url+"?"+temp.toString();
		System.out.println("后台计算签名 字符串:  "+needSign);
		return Md5Util.md5(needSign).toUpperCase();
	}

	/*
	 * public static String createSign(String url, Map<String, String> paramsMap) {
	 * if (paramsMap == null || paramsMap.isEmpty()) { return null; } Map<String,
	 * Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
	 * 
	 * StringBuffer sb = new StringBuffer(); for (Map.Entry<String, String> entry :
	 * paramsMap.entrySet()) { String key = entry.getKey(); String value =
	 * entry.getValue(); sb.append(key + "=" + value + "&"); } // 去除最后一个 & String
	 * paramString = sb.substring(0, sb.length() - 1).toString(); // 加上域名 String
	 * needSign = url + "?" + paramString; System.out.println(needSign); String sign
	 * = Md5Util.md5(needSign).toUpperCase(); System.out.println("签名：" + sign);
	 * 
	 * return sign; }
	 */

	public static void main(String[] args) throws UnsupportedEncodingException {

		Long timestamp = System.currentTimeMillis();
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("uid", "123");
		paramMap.put("token", "84a85522-d418-44b4-8498-9b18cc6ee567");
		paramMap.put("nickname", "美女");
		paramMap.put("timestamp", String.valueOf(timestamp));

		String sign = SignUtil.createSign("http://localhost:8080/users/update/nickname", paramMap,false);
		
		System.out.println(sign);
	}

}
