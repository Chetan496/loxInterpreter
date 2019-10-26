package parser.util;

import lexer.Token;

public class RuntimeError extends RuntimeException {
	final public Token token;

	public RuntimeError(Token token, String message) {
		super(message);
		this.token = token;
	}
}