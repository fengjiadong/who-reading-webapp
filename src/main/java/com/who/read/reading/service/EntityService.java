package com.who.read.reading.service;

import com.who.read.reading.mapper.EntityMapper;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.datamodel.EntityCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname EntityService
 * @date 2020/5/27 4:12 PM
 * @Created by fengjiadong
 */
@Service
public class EntityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityService.class);
	@Autowired
	EntityMapper entityMapper;

	public Integer create(String sql) {
		return entityMapper.create(sql);
	}

	public List<Entity> list(EntityCondition entityCondition) {
		List<Entity> entityList = new ArrayList<>();
		List<Map<String, Object>> list = entityMapper.list(entityCondition.getSql());

		for (int i = 0; i < list.size(); i++) {
			Entity entity = new Entity(entityCondition.getTypeId());
			entity.setId(list.get(i).get("id").toString());
			Map<String, Object> paoperties = list.get(i);
			entity.setProperties(paoperties);
			// 寻找显示值--start
			//fieldDisplay
			HashMap<String, Object> displayMap = new HashMap<>(paoperties);
			String fieldSql = "select * from `dm.field` where parentId = '" + entityCondition.getTypeId() + "'";
			List<Map<String, Object>> fieldList = entityMapper.list(fieldSql);
			for (Map<String, Object> fieldMap : fieldList) {
				String fieldName = fieldMap.get("field").toString();
				if (displayMap.containsKey(fieldName)) {
					// 表示有这个字段，然后用这个字段去查询对应的值并替换原有的值。
					displayMap.put(fieldName, getDisplay(fieldMap, displayMap.get(fieldName)));
				}
			}
			entity.setDisplays(displayMap);
			//--end
			entity.setTypeId(entityCondition.getTypeId());
			entity.setTypeName(entityCondition.getDisplayName());
			entityList.add(entity);
		}

		return entityList;
	}

	public Object getDisplay(Map<String, Object> fieldMap, Object value) {
		Object type = fieldMap.get("type");
		Object schema = fieldMap.get("schema");
		// 如果等于引用ref
		if ("reference".equals(type.toString())) {
			String sql = "select name from " + schema + " where id='" + value + "'";
			Object o = entityMapper.fieldDisplay(sql);
			return o;
		} else if ("option".equals(type.toString())) {
			String sql = "select name from `dm.option` where id= '" + value + "'";
			Object o = entityMapper.fieldDisplay(sql);
			return o;
		}
		return "entity not fount";
	}

	public Map<String, Object> entityInfo(String id) {
		return entityMapper.entityInfo(id);
	}


}
