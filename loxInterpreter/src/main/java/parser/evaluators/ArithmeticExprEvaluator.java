package parser.evaluators;

import lexer.Token;
import parser.util.RuntimeError;
import parser.util.Validator;

public class ArithmeticExprEvaluator implements BinaryExprEvaluator {

	@Override
	public Object evaluate(Token token, Object left, Object right) throws RuntimeError {

		switch (token.tokenType) {
		case PLUS:
			if (left instanceof Double && right instanceof Double) {
				return (Double) left + (Double) right;
			}
			if (left instanceof String && right instanceof String) {
				return (String) left + (String) right;
			}
			throw new RuntimeError(token, "Operands must be two numbers or two strings");
		case MINUS:
			Validator.checkNumberOperands(token, left, right);
			return (Double) left - (Double) right;
		case STAR:
			Validator.checkNumberOperands(token, left, right);
			return (Double) left * (Double) right;
		case SLASH:
			Validator.checkNumberOperands(token, left, right);
			return (Double) left / (Double) right;
		default:
			return null;
		}
	}

}
