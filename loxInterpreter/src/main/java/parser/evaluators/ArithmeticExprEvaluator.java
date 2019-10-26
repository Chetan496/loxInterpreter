package parser.evaluators;

import lexer.Token;
import parser.util.RuntimeError;
import parser.util.StringUtils;
import parser.util.Validator;

public class ArithmeticExprEvaluator implements BinaryExprEvaluator {

	@Override
	public Object evaluate(Token token, Object left, Object right) throws RuntimeError {

		switch (token.tokenType) {
		case PLUS:
			Validator.checkOperandsStringOrNumber(token, left, right);
			if (left instanceof Double && right instanceof Double) {
				return (Double) left + (Double) right;
			} else {
				return "" + StringUtils.stringify(left) + StringUtils.stringify(right);
			}

		case MINUS:
			Validator.checkNumberOperands(token, left, right);
			return (Double) left - (Double) right;
		case STAR:
			Validator.checkNumberOperands(token, left, right);
			return (Double) left * (Double) right;
		case SLASH:
			Validator.checkNumberOperands(token, left, right);
			if ((double) right == 0) {
				throw new RuntimeError(token, "Detected a division by zero.");
			}
			return (Double) left / (Double) right;
		default:
			return null;
		}
	}

}
