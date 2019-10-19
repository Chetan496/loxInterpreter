package parser;

import static lexer.TokenType.BANG;
import static lexer.TokenType.BANG_EQUAL;
import static lexer.TokenType.EOF;
import static lexer.TokenType.EQUAL_EQUAL;
import static lexer.TokenType.FALSE;
import static lexer.TokenType.GREATER;
import static lexer.TokenType.GREATER_EQUAL;
import static lexer.TokenType.LEFT_PAREN;
import static lexer.TokenType.LESS;
import static lexer.TokenType.LESS_EQUAL;
import static lexer.TokenType.MINUS;
import static lexer.TokenType.NIL;
import static lexer.TokenType.NUMBER;
import static lexer.TokenType.PLUS;
import static lexer.TokenType.RIGHT_PAREN;
import static lexer.TokenType.SLASH;
import static lexer.TokenType.STAR;
import static lexer.TokenType.STRING;
import static lexer.TokenType.TRUE;

import java.util.List;

import lexer.Token;
import lexer.TokenType;

public class Parser {

	private final List<Token> tokens;
	private int current = 0;

	Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	private Expr expression() {
		return equality();
	}

	private Expr equality() {

		Expr expr = comparison();

		while (match(BANG_EQUAL, EQUAL_EQUAL)) {
			Token operator = previous();
			Expr right = comparison();
			expr = new Binary(expr, operator, right);
		}

		return expr;
	}

	private Expr comparison() {
		Expr expr = addition();
		while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
			Token operator = previous();
			Expr right = addition();
			expr = new Binary(expr, operator, right);
		}
		return expr;
	}

	private Expr addition() {
		Expr expr = multiplication();
		while (match(PLUS, MINUS)) {
			Token operator = previous();
			Expr right = multiplication();
			expr = new Binary(expr, operator, right);
		}
		return expr;
	}

	private Expr multiplication() {
		Expr expr = unary();
		while (match(STAR, SLASH)) {
			Token operator = previous();
			Expr right = unary();
			expr = new Binary(expr, operator, right);
		}
		return expr;
	}

	private Expr unary() {
		Token currToken = peek();
		if (match(BANG, MINUS)) {

			Expr right = unary();
			return new Unary(currToken, right);
		}
		return primary();
	}

	private Expr primary() {
		if (match(FALSE))
			return new Literal(false);
		if (match(TRUE))
			return new Literal(true);
		if (match(NIL))
			return new Literal(null);

		if (match(NUMBER, STRING)) {
			return new Literal(previous().literal);
		}

		if (match(LEFT_PAREN)) {
			Expr expr = expression();
			consume(RIGHT_PAREN, "Expect ')' after expression.");
			return new Grouping(expr);
		}

		throw new ParseError();

	}

	private void consume(TokenType rightParen, String errorMsg) throws ParseError {

	}

	private boolean match(TokenType... tokenTypes) {
		for (TokenType type : tokenTypes) {
			if (check(type)) {
				advance();
				return true;
			}
		}
		return false;
	}

	private void advance() {
		if (!isAtEnd()) {
			this.current = this.current + 1;
		}
	}

	private boolean check(TokenType type) {
		if (isAtEnd()) {
			return false;
		}
		return peek().tokenType == type;
	}

	private Token peek() {

		return tokens.get(current);
	}

	private boolean isAtEnd() {

		return peek().tokenType == EOF;
	}

	private Token previous() {

		return tokens.get(current - 1);
	}

}
