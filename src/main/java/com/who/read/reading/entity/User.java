package com.who.read.reading.entity;

import org.json.JSONObject;

import java.util.List;

/**
 * @Classname User
 * @date 2020/3/26 5:15 PM
 * @Created by fengjiadong
 */
public class User {
	private String id;
	private String name;
	private Integer age;
	private String userName;
	private String idNumber;
	private String phone;
	private String gender;
	private String password;
	private String img;
	private String email;

	private List<Role> roles;

	public Boolean hasRole(String roleId) {
		if (this.roles == null || this.roles.isEmpty() || roleId == null || "".equals(roleId.trim())) {
			return false;
		}
		for (Role role : this.roles) {
			if (role.getId().equals(roleId)) {
				return true;
			}
		}
		return false;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				", userName='" + userName + '\'' +
				", idNumber='" + idNumber + '\'' +
				", phone='" + phone + '\'' +
				", gender='" + gender + '\'' +
				", password='" + password + '\'' +
				", img='" + img + '\'' +
				'}';
	}

	public JSONObject getJson() {
		JSONObject user = new JSONObject();
		user.put("id", this.id);
		user.put("name", this.name);
		user.put("age", this.age);
		user.put("userName", this.userName);
		user.put("idNumber", this.idNumber);
		user.put("phone", this.phone);
		user.put("gender", this.gender);
		user.put("password", this.password);
		user.put("img", this.img);
		return user;
	}
}
