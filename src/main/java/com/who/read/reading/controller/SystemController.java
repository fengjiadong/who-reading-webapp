package com.who.read.reading.controller;

import com.who.read.reading.configuration.ServiceUtils;
import com.who.read.reading.who.datamodel.Field;
import com.who.read.reading.who.manager.EntityManager;
import com.who.read.reading.who.manager.SystemManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Map;

/**
 * @Classname SystemController
 * @date 2020/6/18 4:37 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/sys")
public class SystemController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);


	@Autowired
	EntityManager entityManager;

	@Autowired
	SystemManager systemManager;

	@RequestMapping("/getField/{id}")
	public Object getField(@PathVariable("id") String id) {
		LOGGER.info("getField(id)", id);
		if (id == null || "".equals(id.trim())) {
			return ServiceUtils.returnMapRestlt("1", "字段Id不能为空！", "");
		}
		Field field = systemManager.getFiledById(id);
		return ServiceUtils.returnMapRestlt("1", "查询成功！", field);
	}

	@RequestMapping("/dateFormats")
	public Object dateFormats() {
		LOGGER.info("dateFormats()");
		Map<String, String> map = systemManager.dateFormats();
		JSONArray jsonArray = new JSONArray();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key",next );
			jsonObject.put("value", map.get(next));
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}

	@RequestMapping("/types")
	public Object types() {
		LOGGER.info("types()");
		Map<String, String> map = systemManager.types();
		JSONArray jsonArray = new JSONArray();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key",next );
			jsonObject.put("value", map.get(next));
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}




}
