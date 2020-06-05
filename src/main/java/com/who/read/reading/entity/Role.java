package com.who.read.reading.entity;

/**
 * @Classname Role
 * @date 2020/5/29 3:45 PM
 * @Created by fengjiadong
 */
public class Role {
	private String id;
	private String name;
	private String displayName;
	private String introduce;
	private String parent;

	public Role(String id) {
		this.id = id;
	}

	public Role(String id, String name, String displayName, String introduce, String parent) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
		this.introduce = introduce;
		this.parent = parent;
	}

	public Role(String id, String name, String displayName, String introduce) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
		this.introduce = introduce;
	}

	public Role(String name, String displayName, String introduce) {
		this.name = name;
		this.displayName = displayName;
		this.introduce = introduce;
	}

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
