package com.who.read.reading.configuration;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Classname JsonManager
 * @date 2020/3/26 11:05 AM
 * @Created by fengjiadong
 */
@Component
public class JsonManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonManager.class);

	/**
	 * 传入一个类的实例，拿到该类的属性名
	 *
	 * @param c
	 * @return 得到该类的所有属性名
	 */
	public static String[] getFiledName(Object c) {
		Field[] fields = c.getClass().getDeclaredFields();
		String[] fieldNamStrings = new String[fields.length];
		for (int i = 0; i < fieldNamStrings.length; i++) {
			fieldNamStrings[i] = fields[i].getName();
		}
		return fieldNamStrings;
	}

	/**
	 * 传入一个Entity类实例，得到该类的Json格式
	 * @param o
	 * @return
	 */
	public static JSONObject getJson(Object o) {
		return getJsonExcludeField(o, null);
	}

	/**
	 * 传入一个Entity类实例，得到该类的Json格式
	 *
	 * @param o
	 * @param key 排除的字段
	 * @return
	 */
	public static JSONObject getJsonExcludeField(Object o, List<String> key) {
		JSONObject jsonObject = new JSONObject();
		String[] filedName = getFiledName(o);
		for (int i = 0; i < filedName.length; i++) {
			try {
				if (key.contains(filedName[i])) {
					continue;
				}
				Object fieldValueByName = getFieldValueByName(filedName[i], o);
				jsonObject.put(filedName[i], fieldValueByName);
			} catch (Exception e) {
				jsonObject.put(filedName[i], "");
			}
		}
		return jsonObject;
	}

	/**
	 * 传入一个Entity类实例，得到该类的Json格式
	 *
	 * @param o
	 * @param key 需要的字段
	 * @return
	 */
	public static JSONObject getJsonNeedField(Object o, List<String> key) {
		JSONObject jsonObject = new JSONObject();
		String[] filedName ={};
		filedName = key.toArray(filedName);
		for (int i = 0; i < filedName.length; i++) {
			try {
				Object fieldValueByName = getFieldValueByName(filedName[i], o);
				jsonObject.put(filedName[i], fieldValueByName);
			} catch (Exception e) {
				jsonObject.put(filedName[i], "");
			}
		}
		return jsonObject;
	}


	/**
	 *      
	 *      @Description 根据属性名 获取值（value）
	 *      @param name
	 *      @param user
	 *     @return
	 *     @throws IllegalAccessException
	 *  
	 */
	public static Object getFieldValueByName(String name, Object o) throws IllegalAccessException {
		String firstletter = name.substring(0, 1).toUpperCase();
		String getter = "get" + firstletter + name.substring(1);
		Method method;
		Object value;
		try {
			method = o.getClass().getMethod(getter, new Class[]{});
			value = method.invoke(o, new Object[]{});
			return value;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T setEntityValue(Class<T> c, JSONObject json) throws IllegalAccessException, InstantiationException {
		T t1 = c.newInstance();
		Field[] t = t1.getClass().getDeclaredFields();
		for (int i = 0; i < t.length; i++) {
			String name = t[i].getName();
			t[i].setAccessible(true);
			Class<?> type = t[i].getType();
			System.out.println(type.getName());
			if (type.getName().contains("Integer") || type.getName().contains("int")) {
				t[i].set(t1, getJsonValue(json, name, Integer.class));
			} else {
				t[i].set(t1, getJsonValue(json, name));
			}
		}
		return t1;
	}

	/**
	 * 传入json 和 key 拿到值
	 *
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public static String getJsonValue(JSONObject jsonObject, String key) {
		return getJsonValue(jsonObject, key, String.class);
	}

	public static <T> T getJsonValue(JSONObject jsonObject, String key, Class<T> c) {
		try {
			return (T) jsonObject.get(key);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
}
