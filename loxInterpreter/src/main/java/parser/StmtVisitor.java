package parser;

public interface StmtVisitor<T> {

	T visit(ExprStmt exprStmt);

	T visit(PrintStmt printStmt);
}
