package parser.evaluators;

import lexer.Token;
import parser.util.RuntimeError;

public interface BinaryExprEvaluator {

	public Object evaluate(final Token token, final Object left, final Object right) throws RuntimeError;
}
