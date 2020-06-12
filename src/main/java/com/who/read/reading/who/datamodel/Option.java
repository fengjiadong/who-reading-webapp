package com.who.read.reading.who.datamodel;

import java.util.List;

/**
 * @Classname Option
 * @date 2020/6/8 4:23 PM
 * @Created by fengjiadong
 */
public class Option {
	String id;
	String name;
	String code;
	String group;
	String explain;
	String parent;
	Boolean isSys;
	Boolean disabled;
	Boolean selectable;
	List<Option> nodes;
	String parentName; // 上级选项名称


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Boolean getSys() {
		return isSys;
	}

	public void setSys(Boolean sys) {
		isSys = sys;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	public List<Option> getNodes() {
		return nodes;
	}

	public void setNodes(List<Option> nodes) {
		this.nodes = nodes;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
