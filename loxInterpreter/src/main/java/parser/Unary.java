package parser;

import lexer.Token;

public class Unary extends Expr {

	final Token operator;
	final Expr expr;

	public Unary(Token token, Expr expr) {
		super();
		this.operator = token;
		this.expr = expr;
	}

	@Override
	<T> T accept(ExprVisitor<T> exprVisitor) {
		return exprVisitor.visit(this);
	}

}
