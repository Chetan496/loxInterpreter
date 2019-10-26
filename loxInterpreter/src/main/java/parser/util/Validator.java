package parser.util;

import lexer.Token;

public class Validator {

	public static void checkNumberOperand(Token operator, Object operand) throws RuntimeError {
		if (operand instanceof Double) {
			return;
		}

		throw new RuntimeError(operator, "Operand must be a number.");
	}

	public static void checkNumberOperands(Token operator, Object left, Object right) throws RuntimeError {

		if (left instanceof Double && right instanceof Double) {
			return;
		}

		throw new RuntimeError(operator, "Operands must be numbers.");
	}

	public static void checkBooleanOperand(Token operator, Object operand) {

		if (operand instanceof Boolean) {
			return;
		}

		throw new RuntimeError(operator, "Operand must be a boolean.");
	}

	public static void checkOperandsStringOrNumber(Token operator, Object left, Object right) {
		if ((left instanceof Double || left instanceof String)
				&& (right instanceof Double || right instanceof String)) {
			return;
		}

		throw new RuntimeError(operator, "Operands must be either a string or a number.");

	}
}
