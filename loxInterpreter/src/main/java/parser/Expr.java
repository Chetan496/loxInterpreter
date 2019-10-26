package parser;

import parser.util.RuntimeError;

public abstract class Expr {

	abstract <T> T accept(ExprVisitor<T> exprVisitor) throws RuntimeError;
}
