package com.who.read.reading.who.datamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Entity
 * @date 2020/5/27 4:03 PM
 * @Created by fengjiadong
 */

public class Entity {

	public Entity(String typeId) {
		this.typeId = typeId;
	}

	private String id;
	private String typeId;
	private String typeName;

	private Map<String, Object> properties;
	private Map<String, Object> displays;

	public Entity() {
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Map<String, Object> getProperties() {
		if(this.properties == null){
			this.properties = new HashMap<>();
		}
		return properties;
	}
	public <T> T getProperty(String key,Class<T> type) {
		Object obj = this.properties.get(key);
		if(obj == null){
			return null;
		}
		return (T)obj;
	}
	public Object getProperty(String key) {
		return this.properties.get(key);
	}

	public Entity setProperty(String key ,Object value) {
		if(this.properties == null){
			this.properties = new HashMap<>();
		}
		this.properties.put(key, value);
		return this;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Map<String, Object> getDisplays() {
		return displays;
	}

	public void setDisplays(Map<String, Object> displays) {
		this.displays = displays;
	}
}
