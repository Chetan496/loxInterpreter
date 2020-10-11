package parser;

import lexer.Token;

public class Var extends Stmt {

	final Token token;

	final Expr initializer;

	public Var(final Token token, final Expr initializer) {
		this.token = token;
		this.initializer = initializer;
	}

	@Override
	<T> T accept(StmtVisitor<T> stmtVisitor) {
		return null;
	}

}
