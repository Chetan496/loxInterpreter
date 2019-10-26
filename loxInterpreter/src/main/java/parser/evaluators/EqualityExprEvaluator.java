package parser.evaluators;

import lexer.TokenType;

public class EqualityExprEvaluator implements BinaryExprEvaluator {

	@Override
	public Object evaluate(TokenType tokenType, Object left, Object right) {

		if (tokenType == TokenType.EQUAL_EQUAL) {
			return left == right;
		}

		if (tokenType == TokenType.BANG_EQUAL) {
			return left != right;
		}

		return null;
	}

}
