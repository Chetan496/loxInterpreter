package parser;

import java.util.List;

import lexer.Lox;
import lexer.Token;
import lexer.TokenType;
import parser.evaluators.BinaryExprEvaluator;
import parser.evaluators.BinaryExprEvaluatorFactory;
import parser.util.RuntimeError;
import parser.util.StringUtils;
import parser.util.Validator;

public class Interpreter implements ExprVisitor<Object>, StmtVisitor<Void> {

	public void interpret(List<Stmt> statements) {
		try {
			for (Stmt stmt : statements) {
				interpretStmt(stmt);
			}

		} catch (RuntimeError error) {
			Lox.runtimeError(error);

		}
	}

	public Object interpret(Expr expr) {
		try {
			Object result = evaluate(expr);
			return result;
		} catch (RuntimeError error) {
			Lox.runtimeError(error);
			return null;
		}

	}

	private Object interpretStmt(Stmt stmt) {
		return stmt.accept(this);
	}

	private Object evaluate(Expr expr) throws RuntimeError {
		return expr.accept(this);
	}

	@Override
	public Void visit(ExprStmt exprStmt) {
		evaluate(exprStmt.expr);
		return null;
	}

	@Override
	public Void visit(PrintStmt printStmt) {
		Object result = evaluate(printStmt.expr);
		System.out.println(StringUtils.stringify(result));
		return null;
	}

	@Override
	public Object visit(Binary binary) throws RuntimeError {

		Object leftVal = binary.left.accept(this);
		Object rightVal = binary.right.accept(this);

		BinaryExprEvaluator binExprEvaluator = BinaryExprEvaluatorFactory
				.getBinaryExprEvaluator(binary.operator.tokenType);

		return binExprEvaluator.evaluate(binary.operator, leftVal, rightVal);
	}

	@Override
	public Object visit(Grouping grouping) throws RuntimeError {
		return grouping.expr.accept(this);
	}

	@Override
	public Object visit(Unary unary) throws RuntimeError {
		Token operator = unary.operator;

		Object exprVal = unary.expr.accept(this);

		if (operator == null) {
			return exprVal;
		}

		if (operator.tokenType == TokenType.MINUS) {
			Validator.checkNumberOperand(operator, exprVal);
			return -1 * (Double) exprVal;
		}

		if (operator.tokenType == TokenType.BANG) {
			Validator.checkBooleanOperand(operator, exprVal);
			return !(Boolean) exprVal;
		}

		return null;
	}

	@Override
	public Object visit(Literal literal) throws RuntimeError {

		return literal.value;

	}

	@Override
	public Object visit(Identifier identifier) throws RuntimeError {
		// look up this variable in the environment and evaluate expr and return it
		return null;
	}

}
