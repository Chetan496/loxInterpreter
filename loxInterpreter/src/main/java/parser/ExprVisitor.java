package parser;

public interface ExprVisitor<T> {

	T visit(Binary binary);

	T visit(Grouping grouping);

	T visit(Literal literal);

	T visit(Unary unary);
}
