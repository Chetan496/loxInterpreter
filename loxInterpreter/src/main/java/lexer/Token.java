package lexer;

public class Token {

	public final String lexeme;
	public final TokenType tokenType;

	final public Object literal;
	final int line;

	public Token(TokenType tokenType, String lexeme, Object literal, int line) {

		this.tokenType = tokenType;
		this.lexeme = lexeme;
		this.literal = literal;
		this.line = line;
	}

	@Override
	public String toString() {
		return "Token [tokenType=" + tokenType + ", lexeme=" + lexeme + ", literal=" + literal + "]";
	}

}
