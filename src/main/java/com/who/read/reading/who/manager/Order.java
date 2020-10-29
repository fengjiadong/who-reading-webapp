package com.who.read.reading.who.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Order
 * @date 2020/10/27 7:24 PM
 * @Created by fengjiadong
 */
public class Order {
	public Order(Sort sort, List<String> fields) {
		this.sort = sort;
		this.fields = fields;
	}
	public Order(){}


	private Sort sort;

	private List<String> fields;

	public List<String> addField(String field){
		if(field == null){
			fields = new ArrayList<String>();
		}
		this.fields.add(field);
		return this.fields;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public enum Sort{
		asc,
		desc
	}
}
