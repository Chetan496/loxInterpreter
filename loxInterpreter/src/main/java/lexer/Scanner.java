package lexer;

import static lexer.TokenType.AND;
import static lexer.TokenType.BANG;
import static lexer.TokenType.BANG_EQUAL;
import static lexer.TokenType.CLASS;
import static lexer.TokenType.COMMA;
import static lexer.TokenType.DOT;
import static lexer.TokenType.ELSE;
import static lexer.TokenType.EQUAL;
import static lexer.TokenType.EQUAL_EQUAL;
import static lexer.TokenType.FALSE;
import static lexer.TokenType.FOR;
import static lexer.TokenType.FUN;
import static lexer.TokenType.GREATER;
import static lexer.TokenType.GREATER_EQUAL;
import static lexer.TokenType.IDENTIFIER;
import static lexer.TokenType.IF;
import static lexer.TokenType.LEFT_BRACE;
import static lexer.TokenType.LEFT_PAREN;
import static lexer.TokenType.LESS;
import static lexer.TokenType.LESS_EQUAL;
import static lexer.TokenType.MINUS;
import static lexer.TokenType.NIL;
import static lexer.TokenType.NUMBER;
import static lexer.TokenType.OR;
import static lexer.TokenType.PLUS;
import static lexer.TokenType.PRINT;
import static lexer.TokenType.RETURN;
import static lexer.TokenType.RIGHT_BRACE;
import static lexer.TokenType.RIGHT_PAREN;
import static lexer.TokenType.SEMICOLON;
import static lexer.TokenType.SLASH;
import static lexer.TokenType.STAR;
import static lexer.TokenType.STRING;
import static lexer.TokenType.SUPER;
import static lexer.TokenType.THIS;
import static lexer.TokenType.TRUE;
import static lexer.TokenType.VAR;
import static lexer.TokenType.WHILE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

	private final String source;

	// start position for current lexeme
	private int start = 0;

	private int current = 0;

	private int currLine = 1;

	private final List<Token> tokens = new ArrayList<>();

	private static final Map<String, TokenType> keywords;

	static {
		keywords = new HashMap<>();
		keywords.put("and", AND);
		keywords.put("class", CLASS);
		keywords.put("else", ELSE);
		keywords.put("false", FALSE);
		keywords.put("for", FOR);
		keywords.put("fun", FUN);
		keywords.put("if", IF);
		keywords.put("nil", NIL);
		keywords.put("or", OR);
		keywords.put("print", PRINT);
		keywords.put("return", RETURN);
		keywords.put("super", SUPER);
		keywords.put("this", THIS);
		keywords.put("true", TRUE);
		keywords.put("var", VAR);
		keywords.put("while", WHILE);
	}

	public Scanner(String source) {
		this.source = source;
	}

	public List<Token> scanTokens() {

		Token token = null;
		while (!isAtEnd()) {
			final char currentChar = advance();

			switch (currentChar) {
			case '(':
				token = new Token(LEFT_PAREN, "(", "(", currLine);
				tokens.add(token);

				break;

			case ')':
				token = new Token(RIGHT_PAREN, ")", ")", currLine);
				tokens.add(token);

				break;

			case '{':
				token = new Token(LEFT_BRACE, "{", "{", currLine);
				tokens.add(token);

				break;
			case '}':
				token = new Token(RIGHT_BRACE, "}", "}", currLine);
				tokens.add(token);

				break;
			case ',':
				token = new Token(COMMA, ",", ",", currLine);
				tokens.add(token);

				break;

			case '.':
				token = new Token(DOT, ".", ".", currLine);
				tokens.add(token);

				break;
			case '-':
				token = new Token(MINUS, "-", "-", currLine);
				tokens.add(token);

				break;
			case '+':
				token = new Token(PLUS, "+", "+", currLine);
				tokens.add(token);

				break;
			case ';':
				token = new Token(SEMICOLON, ";", ";", currLine);
				tokens.add(token);

				break;
			case '/':
				if (match('/'))
					while (peek() != '\n' && !isAtEnd())
						advance();
				else {
					token = new Token(SLASH, "/", "/", currLine);
					tokens.add(token);
				}

				break;
			case '*':
				token = new Token(STAR, "*", "*", currLine);
				tokens.add(token);

				break;
			case '!':

				if (match('=')) {
					token = new Token(BANG_EQUAL, "!=", "!=", currLine);
					tokens.add(token);

				} else {
					token = new Token(BANG, "!", "!", currLine);
					tokens.add(token);

				}
				break;

			case '=':

				if (match('=')) {
					token = new Token(EQUAL_EQUAL, "==", "==", currLine);
					tokens.add(token);

				} else {
					token = new Token(EQUAL, "=", "=", currLine);
					tokens.add(token);

				}
				break;

			case '>':

				if (match('=')) {
					token = new Token(GREATER_EQUAL, ">=", ">=", currLine);
					tokens.add(token);

				} else {
					token = new Token(GREATER, ">", ">", currLine);
					tokens.add(token);

				}
				break;

			case '<':

				if (match('=')) {
					token = new Token(LESS_EQUAL, "<=", "<=", currLine);
					tokens.add(token);

				} else {
					token = new Token(LESS, "<", "<", currLine);
					tokens.add(token);

				}
				break;
			case '"':

				string();
				break;

			case ' ':
			case '\t':
			case '\r':
				break;
			case '\n':
				currLine = currLine + 1;
				break;
			default:
				if (isDigit(currentChar))
					number();
				else if (isAlpha(currentChar))
					identifier();
				else
					Lox.error(currLine, "Unexpected character.");
				break;
			}
		}
		return tokens;
	}

	private void identifier() {
		// either an identifier or a Keyword..
		start = current - 1;
		while (isAlphaNumeric(peek()))
			advance();

		final String maybeIdentifier = source.substring(start, current);
		final TokenType tokenType = keywords.getOrDefault(maybeIdentifier, IDENTIFIER);
		final Token token = new Token(tokenType, maybeIdentifier, maybeIdentifier,
				currLine);
		tokens.add(token);

	}

	private void string() {
		// find the next index where we find a closing quote again. extract that
		// part in a string token
		Token token;
		start = current;
		while (peek() != '"' && !isAtEnd()) {

			if (peek() == '\n')
				currLine = currLine + 1;
			advance();
		}

		if (isAtEnd()) {
			Lox.error(currLine, "Unterminated string");
			return;
		}

		advance();

		final String literal = source.substring(start, current - 1);
		token = new Token(STRING, literal, literal, currLine);
		tokens.add(token);
	}

	private void number() {
		Token token;
		start = current - 1;
		while (isDigit(peek()))
			advance();

		if (peek() == '.' && isDigit(peekNext())) {

			advance(); // consume the "."

			while (isDigit(peek()))
				advance();
		}

		final String numLexeme = source.substring(start, current);
		final Double doubleLiteral = Double.parseDouble(numLexeme);
		token = new Token(NUMBER, numLexeme, doubleLiteral, currLine);
		tokens.add(token);
	}

	private boolean isAtEnd() {
		return current >= source.length();
	}

	private char advance() {
		// return the currentChar and then point to the next character in the source
		final char currentChar = source.charAt(current);
		current = current + 1;
		return currentChar;
	}

	private char peek() {
		return source.charAt(current);
	}

	private boolean match(char expected) {
		if (isAtEnd())
			return false;
		if (source.charAt(current) != expected)
			return false;

		current = current + 1; // will advance only if the next char is matching the
								// expected char
		return true;
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private char peekNext() {
		if (current + 1 >= source.length())
			return '\0';
		return source.charAt(current + 1);
	}

	private boolean isAlpha(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_';
	}

	private boolean isAlphaNumeric(char c) {
		return isAlpha(c) || isDigit(c);
	}

}
