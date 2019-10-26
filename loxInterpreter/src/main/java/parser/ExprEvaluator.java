package parser;

import lexer.Token;
import lexer.TokenType;
import parser.evaluators.BinaryExprEvaluator;
import parser.evaluators.BinaryExprEvaluatorFactory;

/* TODO: runtime error handling.. do not allow things like multiplication of number by a string etc.
 * also simplify the below if else ladder for different binary expressions*/
public class ExprEvaluator implements ExprVisitor<Object> {

	public Object evaluate(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public Object visit(Binary binary) {

		Object leftVal = binary.left.accept(this);
		Object rightVal = binary.right.accept(this);

		BinaryExprEvaluator binExprEvaluator = BinaryExprEvaluatorFactory
				.getBinaryExprEvaluator(binary.operator.tokenType);

		return binExprEvaluator.evaluate(binary.operator.tokenType, leftVal, rightVal);
	}

	@Override
	public Object visit(Grouping grouping) {
		return grouping.expr.accept(this);
	}

	@Override
	public Object visit(Unary unary) {
		Token operator = unary.operator;

		Object exprVal = unary.expr.accept(this); // either a NUMBER, STRING or boolean

		if (exprVal instanceof Double) {

			Double val = (Double) exprVal;
			if (operator == null) {
				return val;
			} else {
				TokenType tokenType = operator.tokenType;
				if (tokenType == TokenType.MINUS) {
					return -1 * val;
				}
			}
		}

		if (exprVal instanceof Boolean) {
			Boolean val = (Boolean) exprVal;
			if (operator == null) {
				return val;
			} else {

				TokenType tokenType = operator.tokenType;
				if (tokenType == TokenType.BANG) {
					return !val;
				}
			}
		}

		if (exprVal instanceof String) {

			String val = (String) exprVal;
			if (operator == null) {
				return val;
			}
		}

		return null;
	}

	@Override
	public Object visit(Literal literal) {

		return literal.value;

	}

	private boolean isEqualityOperator(TokenType operatorTokenType) {
		return operatorTokenType == TokenType.EQUAL_EQUAL || operatorTokenType == TokenType.BANG_EQUAL;
	}

	private boolean isComparisonOperator(TokenType operatorTokenType) {
		return operatorTokenType == TokenType.LESS || operatorTokenType == TokenType.LESS_EQUAL
				|| operatorTokenType == TokenType.GREATER || operatorTokenType == TokenType.GREATER_EQUAL;
	}

}
