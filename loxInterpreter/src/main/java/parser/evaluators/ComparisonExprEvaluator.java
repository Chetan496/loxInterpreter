package parser.evaluators;

import lexer.Token;
import lexer.TokenType;
import parser.util.RuntimeError;
import parser.util.Validator;

public class ComparisonExprEvaluator implements BinaryExprEvaluator {

	@Override
	public Object evaluate(Token token, Object left, Object right) throws RuntimeError {

		Validator.checkNumberOperands(token, left, right);

		Double leftNum = (Double) left;
		Double rightNum = (Double) right;

		if (token.tokenType == TokenType.LESS) {
			return leftNum < rightNum;
		} else if (token.tokenType == TokenType.LESS_EQUAL) {
			return leftNum <= rightNum;
		} else if (token.tokenType == TokenType.GREATER) {
			return leftNum > rightNum;
		} else if (token.tokenType == TokenType.GREATER_EQUAL) {
			return leftNum >= rightNum;
		}

		return null;
	}

}
