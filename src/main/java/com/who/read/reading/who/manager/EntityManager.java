package com.who.read.reading.who.manager;

import com.who.read.reading.service.EntityService;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.datamodel.EntityCondition;
import com.who.read.reading.who.util.UUID;
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

	public Entity getEntity(EntityCondition entityCondition) {
		initSql(entityCondition);
		return entityService.list(entityCondition).get(0);
	}

	public Entity getEntity(String id,String typeId) {
		EntityCondition entityCondition = new EntityCondition(typeId);
		entityCondition.setId(id);
		return getEntity(entityCondition);
	}


	/**
	 * 创建实体
	 * @param entity
	 * @return
	 */
	public String create(Entity entity) {
		Map<String, Object> properties = entity.getProperties();
		Object id = properties.get("id");
		if (id == null) {
			String id1 = entity.getId();
			properties.put("id", id1==null?UUID.generateUUID():id1);
		}
		Map<String, Object> entityInfo = entityService.entityInfo(entity.getTypeId());
		StringBuilder sb = new StringBuilder();
		StringBuilder values = new StringBuilder();
		sb.append("insert into `" + entityInfo.get("name")+"` (");
		values.append("(");
		Iterator<String> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			sb.append("`" + key + "`");
			values.append("'" + properties.get(key) + "'");
			if(keys.hasNext()){
				sb.append(",");
				values.append(",");
			}
		}
		sb.append(")");
		values.append(")");
		Integer result = entityService.create(sb.toString() + " values " + values.toString());
		if (result > 0) {
			return properties.get("id").toString();
		}
		return "";
	}

	/**
	 * 生成查询sql
	 * @param entityCondition
	 */
	private void initSql(EntityCondition entityCondition) {
		Map<String, Object> entity = entityService.entityInfo(entityCondition.getTypeId());
		entityCondition.setName(entity.get("name").toString());
		entityCondition.setDisplayName(entity.get("displayName").toString());
		String name = entityCondition.getName();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ");
		sb.append("FROM `" + name + "` WHERE 1=1 ");
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
