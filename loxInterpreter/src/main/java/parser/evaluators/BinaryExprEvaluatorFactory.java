package parser.evaluators;

import lexer.TokenType;

public class BinaryExprEvaluatorFactory {

	public static BinaryExprEvaluator getBinaryExprEvaluator(TokenType tokenType) {

		switch (tokenType) {
		case PLUS:
		case MINUS:
		case STAR:
		case SLASH:
			return new ArithmeticExprEvaluator();
		case LESS:
		case LESS_EQUAL:
		case GREATER:
		case GREATER_EQUAL:
			return new ComparisonExprEvaluator();
		case BANG_EQUAL:
		case EQUAL_EQUAL:
			return new EqualityExprEvaluator();
		default:
			return null;
		}

	}

}
