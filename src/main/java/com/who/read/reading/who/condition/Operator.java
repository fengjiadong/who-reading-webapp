package com.who.read.reading.who.condition;
/**
 * @Classname Operator
 * @date 2020/6/8 5:51 PM
 * @Created by fengjiadong
 */
public enum  Operator {
	Equals,
	NotEquals,
	Contains,
	ContainsMultiline,
	In,
	NotIn,
	Lt,
	Gt,
	Le,
	Ge,
	StartsWith,
	EndsWith,
	Null,
	NotNull;

	private Operator() {
	}
}
