package com.who.read.reading.who.manager;

import com.who.read.reading.configuration.ErrorConfigurar;
import com.who.read.reading.service.EntityService;
import com.who.read.reading.service.OptionService;
import com.who.read.reading.who.condition.Expression;
import com.who.read.reading.who.condition.FieldExpression;
import com.who.read.reading.who.condition.NestedExpression;
import com.who.read.reading.who.condition.Operator;
import com.who.read.reading.who.datamodel.Columns;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.condition.EntityCondition;
import com.who.read.reading.who.datamodel.Field;
import com.who.read.reading.who.datamodel.Option;
import com.who.read.reading.who.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManager.class);

	@Autowired
	EntityService entityService;

	@Autowired
	OptionService optionService;

	public List<Entity> list(EntityCondition entityCondition) {
		initSql(entityCondition);
		return entityService.list(entityCondition);
	}

	public List<Entity> listAll(String typeId) {
		EntityCondition entityCondition = new EntityCondition(typeId);
		initSql(entityCondition);
		return entityService.list(entityCondition);
	}

	public Entity getEntity(EntityCondition entityCondition) {
		initSql(entityCondition);
		return entityService.list(entityCondition).get(0);
	}

	public Entity getEntity(String id, String typeId) {
		LOGGER.info("getEntity(" + id + "," + typeId + ")");
		EntityCondition entityCondition = new EntityCondition(typeId);
		entityCondition.setId(id);
		return getEntity(entityCondition);
	}




	/**
	 * 创建实体
	 *
	 * @param entity
	 * @return
	 */
	public String create(Entity entity) {
		Map<String, Object> properties = entity.getProperties();
		Object id = properties.get("id");
		if (id == null) {
			String id1 = entity.getId();
			properties.put("id", id1 == null ? UUID.generateUUID() : id1);
		}
		properties.put("typeId", entity.getTypeId());
		Map<String, Object> entityInfo = entityService.entityInfo(entity.getTypeId());
		StringBuilder sb = new StringBuilder();
		StringBuilder values = new StringBuilder();
		sb.append("insert into `" + entityInfo.get("name") + "` (");
		values.append("(");
		Iterator<String> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			sb.append("`" + key + "`");
			values.append("'" + properties.get(key) + "'");
			if (keys.hasNext()) {
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
	 *
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
		// 复杂条件查询
		List<Expression> expressions = entityCondition.getExpressions();
		for (Expression expression : expressions) {
			if (expression instanceof NestedExpression) {
				String sql = nestedExpressionToSql(expression);
				sb.append(" AND " + sql);
			} else {
				String sql = fieldNestedToSql(expression);
				sb.append(" AND" + sql);
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

	/**
	 * 组合条件
	 *
	 * @param expression
	 * @return
	 */
	public String nestedExpressionToSql(Expression expression) {
		NestedExpression nested = (NestedExpression) expression;
		NestedExpression.Operator operator = nested.getOperator();// and/or

		StringBuilder nestSql = new StringBuilder("(");
		List<Expression> expreList = nested.getExpressions();
		Iterator<Expression> iterator = expreList.iterator();
		while (iterator.hasNext()) {
			Expression expre = iterator.next();
			String sql = fieldNestedToSql(expre);
			nestSql.append(sql);
			if (iterator.hasNext()) {
				nestSql.append(" " + operator.name() + " ");
			} else {
				nestSql.append(")");
			}
		}
		return nestSql.toString();
	}

	/**
	 * 字段条件
	 *
	 * @param expression
	 * @return
	 */
	public String fieldNestedToSql(Expression expression) {
		FieldExpression fieldExpression = (FieldExpression) expression;
		String field = fieldExpression.getField();
		Operator operator = fieldExpression.getOperator();
		Object value = fieldExpression.getValue();
		StringBuilder sbSql = new StringBuilder(" `" + field + "` ");
		if (operator == Operator.NotNull) {
			sbSql.append(" IS NOT NULL and `" + field + "` != ''");
		} else if (operator == Operator.Null) {
			sbSql.append(" IS NULL or `" + field + "` = ''");
		} else if (operator == Operator.Contains) {
			sbSql.append(" like '%" + value + "%'");
		} else if (operator == Operator.Equals) {
			sbSql.append(" = '" + value + "'");
		} else if (operator == Operator.NotEquals) {
			sbSql.append(" != '" + value + "'");
		} else if (operator == Operator.In) {
			sbSql.append(" IN " + listToString(value));
		} else if (operator == Operator.NotIn) {
			sbSql.append(" NOT IN " + listToString(value));
		} else {
			return " 1 = 1";
		}
		return "(" + sbSql.toString() + ")";
	}

	public String listToString(Object value) {
		StringBuilder sb = new StringBuilder();
		sb.append("'@#@$%&^%$*'");
		if (value instanceof List) {
			List values = (List) value;
			for (Object o : values) {
				sb.append(",'" + o + "'");
			}
		} else if (value instanceof Object[]) {
			Object[] values = (Object[]) value;
			for (int i = 0; i < values.length; i++) {
				sb.append(",'" + values[i] + "'");
			}
		}
		return "(" + sb.toString() + ")";
	}
}
