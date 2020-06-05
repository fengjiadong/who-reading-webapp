package com.who.read.reading.configuration;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * @Classname ServiceUtils
 * @date 2020/3/26 10:06 AM
 * @Created by fengjiadong
 */
public class ServiceUtils {
	/**
	 * 统一返回格式
	 *
	 * @return
	 */
	public static JSONObject returnRestlt(String code, String msg, Object result) {
		JSONObject results = new JSONObject();
		results.put("code", code);
		results.put("msg", msg);
		results.put("result", result);
		return results;
	}
	/**
	 * 统一返回格式
	 *
	 * @return
	 */
	public static Map<String,Object> returnMapRestlt(String code, String msg, Object result) {
		Map<String, Object> results = new HashMap<>();
		results.put("code", code);
		results.put("msg", msg);
		results.put("result", result);
		return results;
	}
}
