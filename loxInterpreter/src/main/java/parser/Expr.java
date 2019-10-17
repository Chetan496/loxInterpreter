package parser;

public abstract class Expr {

	abstract <T> T accept(ExprVisitor<T> exprVisitor);
}
