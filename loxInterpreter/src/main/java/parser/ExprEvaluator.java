package parser;

import lexer.Token;
import lexer.TokenType;

public class ExprEvaluator implements ExprVisitor<Object> {

	public Object evaluate(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public Object visit(Binary binary) {
		Token operator = binary.operator;
		Object leftVal = binary.left.accept(this);
		Object rightVal = binary.right.accept(this);

		TokenType operatorTokenType = operator.tokenType;

		if (operatorTokenType == TokenType.STAR) {

			if (leftVal instanceof Double && rightVal instanceof Double) {
				Double leftNum = (Double) leftVal;
				Double rightNum = (Double) rightVal;

				return leftNum * rightNum;
			}
		} else if (operatorTokenType == TokenType.SLASH) {
			if (leftVal instanceof Double && rightVal instanceof Double) {
				Double leftNum = (Double) leftVal;
				Double rightNum = (Double) rightVal;

				return leftNum / rightNum;
			}
		} else if (operatorTokenType == TokenType.MINUS) {

			if (leftVal instanceof Double && rightVal instanceof Double) {
				Double leftNum = (Double) leftVal;
				Double rightNum = (Double) rightVal;

				return leftNum - rightNum;
			}
		} else if (operatorTokenType == TokenType.PLUS) {

			if (leftVal instanceof Double && rightVal instanceof Double) {
				Double leftNum = (Double) leftVal;
				Double rightNum = (Double) rightVal;

				return leftNum + rightNum;
			}

			if (leftVal instanceof String && rightVal instanceof String) {
				String leftStr = (String) leftVal;
				String rightStr = (String) rightVal;

				return leftStr + rightStr;
			}
		} else if (isComparisonOperator(operatorTokenType)) {

			if (leftVal instanceof Double && rightVal instanceof Double) {
				Double leftNum = (Double) leftVal;
				Double rightNum = (Double) rightVal;

				if (operatorTokenType == TokenType.LESS) {
					return leftNum < rightNum;
				} else if (operatorTokenType == TokenType.LESS_EQUAL) {
					return leftNum <= rightNum;
				} else if (operatorTokenType == TokenType.GREATER) {
					return leftNum > rightNum;
				} else if (operatorTokenType == TokenType.GREATER_EQUAL) {
					return leftNum >= rightNum;
				}

			}

		} else if (isEqualityOperator(operatorTokenType)) {

			if (operatorTokenType == TokenType.EQUAL_EQUAL) {
				if (leftVal instanceof Double && rightVal instanceof Double) {

					return leftVal == rightVal;
				}

				if (leftVal instanceof Boolean && rightVal instanceof Boolean) {

					return leftVal == rightVal;
				}
			}

			if (operatorTokenType == TokenType.BANG_EQUAL) {
				if (leftVal instanceof Double && rightVal instanceof Double) {

					return leftVal != rightVal;
				}

				if (leftVal instanceof Boolean && rightVal instanceof Boolean) {

					return leftVal != rightVal;
				}
			}

		}
		return null;
	}

	private boolean isEqualityOperator(TokenType operatorTokenType) {
		return operatorTokenType == TokenType.EQUAL_EQUAL || operatorTokenType == TokenType.BANG_EQUAL;
	}

	private boolean isComparisonOperator(TokenType operatorTokenType) {
		return operatorTokenType == TokenType.LESS || operatorTokenType == TokenType.LESS_EQUAL
				|| operatorTokenType == TokenType.GREATER || operatorTokenType == TokenType.GREATER_EQUAL;
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

}
