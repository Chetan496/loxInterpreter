package parser;

public class Grouping extends Expr {

	final Expr expr;

	public Grouping(Expr expr) {
		super();
		this.expr = expr;
	}

	@Override
	<T> T accept(ExprVisitor<T> exprVisitor) {
		return exprVisitor.visit(this);
	}

}
