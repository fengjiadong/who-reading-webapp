package com.who.read.reading.who.datamodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname Menu
 * @date 2020/5/29 2:04 PM
 * @Created by fengjiadong
 */
public class Menu {
	private Menu parent;
	private String id;
	private String name;
	private String parentId;
	private String parentName;
	private String icon;
	private String code;
	private String action;
	private boolean selectable;
	private boolean disabled;
	private String count;
	private Integer order;
	private String site;
	private String explain;
	private boolean validity;
	private Map<String, Object> state;

	public Map<String, Object> getState() {
		if (state == null) {
			state = new HashMap<>();
			state.put("expanded", false);
			state.put("selected", false);
			state.put("disabled", disabled);
		}
		return state;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setState(Map<String, Object> state) {
		this.state = state;
	}


	// 下级节点
	private List<Menu> nodes;

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public boolean isValidity() {
		return validity;
	}

	public void setValidity(boolean validity) {
		this.validity = validity;
	}

	public List<Menu> getNodes() {
		return nodes;
	}

	public void setNodes(List<Menu> nodes) {
		this.nodes = nodes;
	}
}
