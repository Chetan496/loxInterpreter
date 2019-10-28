package parser;

public class ExprStmt extends Stmt {

	final Expr expr;

	public ExprStmt(final Expr exprInit) {
		this.expr = exprInit;
	}

	@Override
	<T> T accept(StmtVisitor<T> stmtVisitor) {
		return stmtVisitor.visit(this);
	}

}
