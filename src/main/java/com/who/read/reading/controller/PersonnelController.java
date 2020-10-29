package com.who.read.reading.controller;

import com.who.read.reading.utils.Options;
import com.who.read.reading.who.condition.EntityCondition;
import com.who.read.reading.who.condition.Operator;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.manager.EntityManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname PersonnelController
 * @date 2020/10/21 8:55 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/person")
public class PersonnelController {
	@Autowired
	EntityManager entityManager;


	@RequestMapping("/get/{id}")
	public Object getPerson(@PathVariable("id") String id) {
		Entity entity = entityManager.getEntity(id, Options.PersonTypeId);
		Map<String, Object> properties = entity.getProperties();
		return properties;
	}

	@RequestMapping("/search")
	public Object searchPerson(HttpServletRequest request) {
		EntityCondition entityCondition = new EntityCondition(Options.PersonTypeId);
		entityCondition.addFieldExpression("name", Operator.Contains, "颜");
		List<Entity> entityList = entityManager.list(entityCondition);
//		List<Entity> entityList = entityManager.listAll("95f796c12215bb624634d12a9ca9bd8b");
		request.setAttribute("entityList", entityList);
		for (Entity entityOne : entityList) {
			System.out.println(entityOne.getProperty("id"));
			System.out.println(entityOne.getProperty("name"));
		}
		return entityList;
	}


	@RequestMapping("/create")
	public Object create(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		String[] files = parameterMap.get("files[]");
		Entity entity = new Entity(Options.PersonTypeId);
		entity.setProperty("name", request.getParameter("name"));
		if (files != null) {
			JSONArray filesArray = new JSONArray();
			for (int i = 0; i < files.length; i++) {
				filesArray.put(files[i]);
			}
			System.out.println(filesArray);
			entity.setProperty("imgs", filesArray);
		}
		entity.setProperty("address", request.getParameter("address"));
		entity.setProperty("zone", request.getParameter("zone"));
		entity.setProperty("type", request.getParameter("type"));
		entity.setProperty("age", request.getParameter("age"));
		entity.setProperty("qq", setNull(request,"qq"));
		entity.setProperty("weixin", setNull(request,"weixin"));
		entity.setProperty("priceBy", request.getParameter("priceBy"));
		entity.setProperty("priceOne", request.getParameter("priceOne"));
		entity.setProperty("priceTow", request.getParameter("priceTow"));
		entity.setProperty("timeBy", request.getParameter("timeBy"));
		entity.setProperty("remarks", setNull(request,"remarks"));
		entity.setProperty("phone", setNull(request,"phone"));
		entity.setProperty("createTime", new Date());
		String entityId = entityManager.create(entity);
		JSONObject result = new JSONObject();
		result.put("code", "1");
		result.put("entityId", entityId);
		return result.toString();
	}


	@RequestMapping("/update")
	public Object update(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("code", "0");
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			result.put("msg", "请传入Id");
			return result.toString();
		}
		Entity entity = entityManager.getEntity(id, Options.PersonTypeId);
		Map<String, String[]> parameterMap = request.getParameterMap();
		String[] files = parameterMap.get("files[]");
		entity.setProperty("name", request.getParameter("name"));
		if (files != null) {
			JSONArray filesArray = new JSONArray();
			for (int i = 0; i < files.length; i++) {
				filesArray.put(files[i]);
			}
			System.out.println(filesArray);
			entity.setProperty("imgs", filesArray);
		}
		entity.setProperty("address", request.getParameter("address"));
		entity.setProperty("zone", request.getParameter("zone"));
		entity.setProperty("type", request.getParameter("type"));
		entity.setProperty("age", request.getParameter("age"));
		entity.setProperty("qq", setNull(request,"qq"));
		entity.setProperty("weixin", setNull(request,"weixin"));
		entity.setProperty("priceBy", request.getParameter("priceBy"));
		entity.setProperty("priceOne", request.getParameter("priceOne"));
		entity.setProperty("priceTow", request.getParameter("priceTow"));
		entity.setProperty("timeBy", request.getParameter("timeBy"));
		entity.setProperty("remarks", setNull(request,"remarks"));
		entity.setProperty("phone", setNull(request,"phone"));
		entity.setProperty("createTime", new Date());
		String entityId = entityManager.update(entity);
		result.put("code", "1");
		result.put("entityId", entityId);
		return result.toString();
	}
	public String setNull(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		if(StringUtils.isEmpty(value)){
			return "暂无";
		}
		return value;
	}


}
