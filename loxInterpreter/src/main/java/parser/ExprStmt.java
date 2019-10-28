package parser;

public class ExprStmt extends Stmt {

	final Expr expr;

	public ExprStmt(final Expr exprInit) {
		this.expr = exprInit;
	}
}
