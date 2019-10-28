package parser;

public abstract class Stmt {

	abstract <T> T accept(StmtVisitor<T> stmtVisitor);

}
