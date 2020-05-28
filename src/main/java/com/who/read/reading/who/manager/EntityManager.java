package com.who.read.reading.who.manager;

import com.who.read.reading.service.EntityService;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.datamodel.EntityCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname EntityManager
 * @date 2020/5/27 4:46 PM
 * @Created by fengjiadong
 */
@Component
public class EntityManager {
	@Autowired
	EntityService entityService;

	public List<Entity> list(EntityCondition entityCondition) {

		initSql(entityCondition);
		return entityService.list(entityCondition);
	}


//	private void initDiaplaySql(EntityCondition entityCondition) {
//		Map<String, Object> entity = entityService.entityInfo(entityCondition.getTypeId());
//		entityCondition.setName(entity.get("name").toString());
//		entityCondition.setDisplayName(entity.get("displayName").toString());
//		String name = entityCondition.getName();
//
//		// 查询语句
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT * ");
//		// 得到字段
//		EntityCondition fieldcon = new EntityCondition("11f791ca9b62462a9d8bc12215bb634d");
//		fieldcon.setProperty("parentId", entityCondition.getTypeId());
//		initSql(fieldcon);
//		List<Entity> list = entityService.list(fieldcon);
//
//		for (Entity key : list) {
//			System.out.println(key.getProperty("type"));
//			System.out.println(key.getProperty("field"));
//			System.out.println(key.getProperty("parentEntity"));
//			System.out.println(key.getProperty("schema"));
//		}
//
//
//		sb.append("FROM " + name + " WHERE 1=1 ");
//		// 判断条件
//		Map<String, Object> properties = entityCondition.getProperties();
//		if (properties != null) {
//			sb.append(" AND ");
//			Iterator<String> iterator = properties.keySet().iterator();
//			while (iterator.hasNext()) {
//				String next = iterator.next();
//				sb.append(next + " = '" + properties.get(next) + "' ");
//				if (iterator.hasNext()) {
//					sb.append(" AND ");
//				}
//			}
//		}
//		// 设置Id
//		List<String> ids = entityCondition.getIds();
//		if (ids != null) {
//			StringBuilder idSql = new StringBuilder();
//			idSql.append("(");
//			Iterator<String> iterator = ids.iterator();
//			while (iterator.hasNext()) {
//				String next = iterator.next();
//				idSql.append("'" + next + "'");
//				if (iterator.hasNext()) {
//					idSql.append(",");
//				}
//			}
//			idSql.append(")");
//			sb.append(" AND id in " + idSql.toString());
//		}
//
//		entityCondition.setSqlDispaly(sb.toString());
//	}

//	public void initPropertiesSql(EntityCondition entityCondition) {
//
//	}

	private void initSql(EntityCondition entityCondition) {
		Map<String, Object> entity = entityService.entityInfo(entityCondition.getTypeId());
		entityCondition.setName(entity.get("name").toString());
		entityCondition.setDisplayName(entity.get("displayName").toString());
		String name = entityCondition.getName();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ");
		sb.append("FROM " + name + " WHERE 1=1 ");
		// 判断条件
		Map<String, Object> properties = entityCondition.getProperties();
		if (properties != null) {
			sb.append(" AND ");
			Iterator<String> iterator = properties.keySet().iterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				sb.append(next + " = '" + properties.get(next) + "' ");
				if (iterator.hasNext()) {
					sb.append(" AND ");
				}
			}
		}
		// 设置Id
		List<String> ids = entityCondition.getIds();
		if (ids != null) {
			StringBuilder idSql = new StringBuilder();
			idSql.append("(");
			Iterator<String> iterator = ids.iterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				idSql.append("'" + next + "'");
				if (iterator.hasNext()) {
					idSql.append(",");
				}
			}
			idSql.append(")");
			sb.append(" AND id in " + idSql.toString());
		}

		entityCondition.setSql(sb.toString());
	}
	// 引用

}
