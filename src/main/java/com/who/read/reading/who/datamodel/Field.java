package com.who.read.reading.who.datamodel;

/**
 * @Classname Field
 * @date 2020/6/17 2:07 PM
 * @Created by fengjiadong
 */
public class Field {

	String id;

	Field parent;

	String type; // 字段类型

	String typeId; //字段所属typeId

	String name;

	String display;

	String displayAs;

	String description;

	String schema;

	String format;


	Integer index;

	boolean isSystem;

	boolean isArray;


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
}
