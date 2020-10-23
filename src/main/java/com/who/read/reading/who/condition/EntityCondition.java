package com.who.read.reading.who.condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname EntityCondition
 * @date 2020/5/27 4:27 PM
 * @Created by fengjiadong
 */
public class EntityCondition {
	public EntityCondition(String typeId) {
		this.typeId = typeId;
	}

	// 设置Ids
	private String id;
	private List<String> ids;

	/**
	 * 不在该id集合中
	 */
	private List<String> ninIds;

	private String typeId;
	private String name;
	private String displayName;
	private Map<String, Object> properties;
	private String sql;
	private String sqlDispaly;
	private List<Expression> expressions;

	private Integer pageNo;
	private Integer pageSize;


	public String getTypeId() {
		return typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setProperty(String key, String value) {
		if (this.properties == null) {
			this.properties = new HashMap<>();
		}
		this.properties.put(key, value);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setId(String id) {
		this.ids = new ArrayList<>();
		this.ids.add(id);
	}

	public void setAddId(String id) {
		if(this.ids == null){
			this.ids = new ArrayList<>();
		}
		this.ids.add(id);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSqlDispaly() {
		return sqlDispaly;
	}

	public void setSqlDispaly(String sqlDispaly) {
		this.sqlDispaly = sqlDispaly;
	}

	// 高级查询相关--------------------------------------------------
	public List<Expression> getExpressions() {
		if (this.expressions == null) {
			this.expressions = new ArrayList();
		}
		return this.expressions;
	}
	public EntityCondition addExpression(Expression exp) {
		this.getExpressions().add(exp);
		return this;
	}
	public EntityCondition addFieldExpression(String field, Operator operator, Object value) {
		return this.addExpression(new FieldExpression(field, operator, value));
	}

	public EntityCondition addNestedExpression(NestedExpression.Operator operator, Expression... expressions) {
		return this.addExpression(new NestedExpression(operator, expressions));
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
