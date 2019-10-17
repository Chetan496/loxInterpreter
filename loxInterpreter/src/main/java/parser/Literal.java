package parser;

public class Literal extends Expr {

	final Object value;

	public Literal(Object value) {
		super();
		this.value = value;
	}

	@Override
	<T> T accept(ExprVisitor<T> exprVisitor) {
		return exprVisitor.visit(this);
	}

}
