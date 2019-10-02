package lexer;

import static lexer.TokenType.BANG;
import static lexer.TokenType.BANG_EQUAL;
import static lexer.TokenType.COMMA;
import static lexer.TokenType.DOT;
import static lexer.TokenType.EQUAL;
import static lexer.TokenType.EQUAL_EQUAL;
import static lexer.TokenType.GREATER;
import static lexer.TokenType.GREATER_EQUAL;
import static lexer.TokenType.LEFT_BRACE;
import static lexer.TokenType.LEFT_PAREN;
import static lexer.TokenType.LESS;
import static lexer.TokenType.LESS_EQUAL;
import static lexer.TokenType.MINUS;
import static lexer.TokenType.PLUS;
import static lexer.TokenType.RIGHT_BRACE;
import static lexer.TokenType.RIGHT_PAREN;
import static lexer.TokenType.SEMICOLON;
import static lexer.TokenType.SLASH;
import static lexer.TokenType.STAR;
import static lexer.TokenType.STRING;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

	private final String source;
	private int currCharIndex = 0;

	private int currLine = 1;

	public Scanner(String source) {
		this.source = source;
	}

	public List<Token> scanTokens() {

		final List<Token> tokens = new ArrayList<>();
		final int length = source.length();
		Token token = null;
		while (currCharIndex < length) {
			final char currentChar = source.charAt(currCharIndex);
			char nextChar;

			switch (currentChar) {
			case '(':
				token = new Token(LEFT_PAREN, "(", "(", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;

			case ')':
				token = new Token(RIGHT_PAREN, ")", ")", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;

			case '{':
				token = new Token(LEFT_BRACE, "{", "{", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;
			case '}':
				token = new Token(RIGHT_BRACE, "}", "}", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;
			case ',':
				token = new Token(COMMA, ",", ",", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;

			case '.':
				token = new Token(DOT, ".", ".", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;
			case '-':
				token = new Token(MINUS, "-", "-", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;
			case '+':
				token = new Token(PLUS, "+", "+", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;
			case ';':
				token = new Token(SEMICOLON, ";", ";", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;
			case '/':
				token = new Token(SLASH, "/", "/", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;
			case '*':
				token = new Token(STAR, "*", "*", currLine);
				tokens.add(token);
				incrementCurrCharIndex(1);
				break;
			case '!':
				nextChar = source.charAt(currCharIndex + 1);
				if (nextChar == '=') {
					token = new Token(BANG_EQUAL, "!=", "!=", currLine);
					tokens.add(token);
					incrementCurrCharIndex(2);

				} else {
					token = new Token(BANG, "!", "!", currLine);
					tokens.add(token);
					incrementCurrCharIndex(1);
				}
				break;

			case '=':
				nextChar = source.charAt(currCharIndex + 1);
				if (nextChar == '=') {
					token = new Token(EQUAL_EQUAL, "==", "==", currLine);
					tokens.add(token);
					incrementCurrCharIndex(2);

				} else {
					token = new Token(EQUAL, "=", "=", currLine);
					tokens.add(token);
					incrementCurrCharIndex(1);
				}
				break;

			case '>':
				nextChar = source.charAt(currCharIndex + 1);
				if (nextChar == '=') {
					token = new Token(GREATER_EQUAL, ">=", ">=", currLine);
					tokens.add(token);
					incrementCurrCharIndex(2);

				} else {
					token = new Token(GREATER, ">", ">", currLine);
					tokens.add(token);
					incrementCurrCharIndex(1);
				}
				break;

			case '<':
				nextChar = source.charAt(currCharIndex + 1);
				if (nextChar == '=') {
					token = new Token(LESS_EQUAL, "<=", "<=", currLine);
					tokens.add(token);
					incrementCurrCharIndex(2);

				} else {
					token = new Token(LESS, "<", "<", currLine);
					tokens.add(token);
					incrementCurrCharIndex(1);
				}
				break;
			case '"':
				// find the next index where we find a closing quote again. extract that
				// part in a string token
				int i = 1;
				do
					nextChar = source.charAt(currCharIndex + i++);
				while (nextChar != '"');
				if (currCharIndex + i > length) {
					// TODO: raise an error
				}

				final String literal = source.substring(currCharIndex + 1,
						currCharIndex + i);
				token = new Token(STRING, literal, literal, currLine);
				tokens.add(token);
				incrementCurrCharIndex(i + 1);
				break;

			case ' ':
				break;
			case '\n':
				currLine = currLine + 1;
				break;
			default:
				break;
			}
		}
		return tokens;
	}

	private void incrementCurrCharIndex(int lengthOfTokenConsumed) {
		currCharIndex = currCharIndex + lengthOfTokenConsumed;

	}

}
