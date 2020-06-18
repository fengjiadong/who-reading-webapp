package com.who.read.reading.who.datamodel;

/**
 * @Classname Field
 * @date 2020/6/17 2:07 PM
 * @Created by fengjiadong
 */
public class Field {

	private String id;

	private Field parent;

	private String type; // 字段类型

	private String typeId; //字段所属typeId

	private String name;

	private String display;

	private String displayAs;

	private String description;

	private String schema;

	private String schemaAs;

	private String format;

	private Integer length;


	private Integer index;

	private boolean isSystem;

	private boolean isArray;


	private String defaultValue;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Field getParent() {
		return parent;
	}

	public void setParent(Field parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDisplayAs() {
		return displayAs;
	}

	public void setDisplayAs(String displayAs) {
		this.displayAs = displayAs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean system) {
		isSystem = system;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean array) {
		isArray = array;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getSchemaAs() {
		return schemaAs;
	}

	public void setSchemaAs(String schemaAs) {
		this.schemaAs = schemaAs;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
