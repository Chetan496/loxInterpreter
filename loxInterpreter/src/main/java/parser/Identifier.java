package parser;

import lexer.Token;
import parser.util.RuntimeError;

public class Identifier extends Expr {

	final Token token;

	public Identifier(final Token token) {
		this.token = token;
	}

	@Override
	<T> T accept(ExprVisitor<T> exprVisitor) throws RuntimeError {
		return exprVisitor.visit(this);
	}

}
