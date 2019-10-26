package parser.evaluators;

import lexer.TokenType;

public class ArithmeticExprEvaluator implements BinaryExprEvaluator {

	@Override
	public Object evaluate(TokenType tokenType, Object left, Object right) {

		double leftval = (Double) left;
		double rightval = (Double) right;

		switch (tokenType) {
		case PLUS:
			return leftval + rightval;
		case MINUS:
			return leftval - rightval;
		case STAR:
			return leftval * rightval;
		case SLASH:
			return leftval / rightval;
		default:
			return null;
		}
	}

}
