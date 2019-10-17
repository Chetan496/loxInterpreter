package parser;

public class AstPrinter implements ExprVisitor<String> {

	public String print(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public String visit(Binary binary) {

		return parenthesize(binary.operator.lexeme, binary.left, binary.right);
	}

	@Override
	public String visit(Grouping grouping) {

		return parenthesize("group", grouping.expr);
	}

	@Override
	public String visit(Literal literal) {

		if (literal.value == null)
			return "nil";
		return literal.value.toString();
	}

	@Override
	public String visit(Unary unary) {

		return parenthesize(unary.operator.lexeme, unary.expr);
	}

	private String parenthesize(String name, Expr... exprs) {

		final StringBuilder builder = new StringBuilder();
		builder.append("(").append(name);
		for (final Expr expr : exprs) {
			builder.append(" ");
			builder.append(expr.accept(this));
		}
		builder.append(")");
		return builder.toString();
	}

}
