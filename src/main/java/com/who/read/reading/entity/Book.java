package com.who.read.reading.entity;

import org.json.JSONObject;

/**
 * @Classname Book
 * @date 2020/3/26 9:53 AM
 * @Created by fengjiadong
 */
public class Book {
	private Integer id;
	private String name;
	private String img;
	private String num;
	private String author;
	private String introduce;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + this.id +
				", name='" + this.name + '\'' +
				", img='" + this.img + '\'' +
				", num='" + this.num + '\'' +
				", author='" + this.author + '\'' +
				", introduce='" + this.introduce + '\'' +
				'}';
	}


}
