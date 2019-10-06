package parser;

import lexer.Token;

public class Unary extends Expr {

	final Token token;
	final Expr expr;

	public Unary(Token token, Expr expr) {
		super();
		this.token = token;
		this.expr = expr;
	}

}
