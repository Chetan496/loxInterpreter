package parser;

import lexer.Token;

public class Binary extends Expr {

	final Expr left;
	final Token operator;
	final Expr right;

	public Binary(Expr left, Token operator, Expr right) {
		super();
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

}
