package parser.evaluators;

import lexer.TokenType;

public class ComparisonExprEvaluator implements BinaryExprEvaluator {

	@Override
	public Object evaluate(TokenType tokenType, Object left, Object right) {

		if (left instanceof Double && right instanceof Double) {

			Double leftNum = (Double) left;
			Double rightNum = (Double) right;

			if (tokenType == TokenType.LESS) {
				return leftNum < rightNum;
			} else if (tokenType == TokenType.LESS_EQUAL) {
				return leftNum <= rightNum;
			} else if (tokenType == TokenType.GREATER) {
				return leftNum > rightNum;
			} else if (tokenType == TokenType.GREATER_EQUAL) {
				return leftNum >= rightNum;
			}

		}

		return null;
	}

}
