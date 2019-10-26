package parser.evaluators;

import lexer.Token;
import lexer.TokenType;

public class EqualityExprEvaluator implements BinaryExprEvaluator {

	@Override
	public Object evaluate(Token token, Object left, Object right) {

		if (token.tokenType == TokenType.EQUAL_EQUAL) {
			return left == right;
		}

		if (token.tokenType == TokenType.BANG_EQUAL) {
			return left != right;
		}

		return null;
	}

}
