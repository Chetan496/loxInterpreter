package parser.evaluators;

import lexer.TokenType;

public interface BinaryExprEvaluator {

	public Object evaluate(final TokenType tokenType, final Object left, final Object right);
}
