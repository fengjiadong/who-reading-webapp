package com.who.read.reading.who.condition;

/**
 * @Classname FieldExpression
 * @date 2020/6/8 5:53 PM
 * @Created by fengjiadong
 */
public class FieldExpression implements Expression {

	private static final long serialVersionUID = 6850000684870565937L;
	private String field;
	private Operator operator;
	private Object value;

	public FieldExpression() {
	}

	public FieldExpression(String field, Operator operator, Object value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	public boolean isNested() {
		return false;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Operator getOperator() {
		return this.operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
}
