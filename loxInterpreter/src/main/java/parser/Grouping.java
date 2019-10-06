package parser;

public class Grouping extends Expr {

	final Expr expr;

	public Grouping(Expr expr) {
		super();
		this.expr = expr;
	}

}
