package com.who.read.reading.who.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname NestedExpression
 * @date 2020/6/8 5:57 PM
 * @Created by fengjiadong
 */
public class NestedExpression implements Expression{
	private static final long serialVersionUID = 3717250968445093966L;
	private List<Expression> expressions = new ArrayList();
	private NestedExpression.Operator operator;


	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public boolean isNested() {

		return true;
	}
	public NestedExpression(NestedExpression.Operator operator, List<Expression> expressions) {
		this.operator = operator;
		this.expressions.addAll(expressions);
	}
	public NestedExpression(NestedExpression.Operator operator, Expression... expressions) {
//		this.operator = NestedExpression.Operator.AND;
		this.operator = operator;
		Expression[] var3 = expressions;
		int var4 = expressions.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			Expression expression = var3[var5];
			this.expressions.add(expression);
		}

	}
	public List<Expression> getExpressions() {
		if (this.expressions == null) {
			this.expressions = new ArrayList();
		}

		return this.expressions;
	}
	public NestedExpression() {
		this.operator = NestedExpression.Operator.AND;
	}

	public static enum Operator {
		AND,
		OR;

		private Operator() {
		}
	}
}
