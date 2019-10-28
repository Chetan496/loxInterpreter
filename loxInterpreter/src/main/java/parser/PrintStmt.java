package parser;

public class PrintStmt extends Stmt {

	final Expr expr;

	public PrintStmt(final Expr exprInit) {
		this.expr = exprInit;
	}
}
