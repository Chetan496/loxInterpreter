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
import static lexer.TokenType.PRINT;
import static lexer.TokenType.RIGHT_PAREN;
import static lexer.TokenType.SEMICOLON;
import static lexer.TokenType.SLASH;
import static lexer.TokenType.STAR;
import static lexer.TokenType.STRING;
import static lexer.TokenType.TRUE;

import java.util.ArrayList;
import java.util.List;

import lexer.Lox;
import lexer.Token;
import lexer.TokenType;

public class Parser {

	private final List<Token> tokens;
	private int current = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Stmt> parse() {

		List<Stmt> statements = new ArrayList<>();
		while (!isAtEnd()) {
			try {
				statements.add(statement());
			} catch (ParseError e) {
				System.err.println(e);
			}
		}
		return statements;
	}

	private Stmt statement() throws ParseError {

		if (match(PRINT)) {
			Stmt stmt = new PrintStmt(expression());
			consume(SEMICOLON, "Expected a semicolon after statement");
			return stmt;
		} else {
			Stmt stmt = new ExprStmt(expression());
			consume(SEMICOLON, "Expected a semicolon after expression");
			return stmt;
		}

	}

	private Expr expression() throws ParseError {
		return equality();
	}

	private Expr equality() throws ParseError {

		Expr expr = comparison();

		while (match(BANG_EQUAL, EQUAL_EQUAL)) {
			final Token operator = previous();
			final Expr right = comparison();
			expr = new Binary(expr, operator, right);
		}

		return expr;
	}

	private Expr comparison() throws ParseError {
		Expr expr = addition();
		while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
			final Token operator = previous();
			final Expr right = addition();
			expr = new Binary(expr, operator, right);
		}
		return expr;
	}

	private Expr addition() throws ParseError {
		Expr expr = multiplication();
		while (match(PLUS, MINUS)) {
			final Token operator = previous();
			final Expr right = multiplication();
			expr = new Binary(expr, operator, right);
		}
		return expr;
	}

	private Expr multiplication() throws ParseError {
		Expr expr = unary();
		while (match(STAR, SLASH)) {
			final Token operator = previous();
			final Expr right = unary();
			expr = new Binary(expr, operator, right);
		}
		return expr;
	}

	private Expr unary() throws ParseError {
		final Token currToken = peek();
		if (match(BANG, MINUS)) {

			final Expr right = unary();
			return new Unary(currToken, right);
		}
		return primary();
	}

	private Expr primary() throws ParseError {
		if (match(FALSE))
			return new Literal(false);
		if (match(TRUE))
			return new Literal(true);
		if (match(NIL))
			return new Literal(null);

		if (match(NUMBER, STRING))
			return new Literal(previous().literal);

		if (match(LEFT_PAREN)) {
			final Expr expr = expression();
			consume(RIGHT_PAREN, "Expect ')' after expression.");
			return new Grouping(expr);
		}

		throw error(peek(), "Expect expression.");

	}

	private Token consume(TokenType tokenType, String errorMsg) throws ParseError {

		if (check(tokenType))
			return advance();

		throw error(peek(), errorMsg);
	}

	private void synchronize() {

		advance();
		while (!isAtEnd()) {

			if (previous().tokenType == SEMICOLON)
				return;

			switch (peek().tokenType) {
			case CLASS:
			case FUN:
			case VAR:
			case FOR:
			case IF:
			case WHILE:
			case PRINT:
			case RETURN:
				return;
			}

			advance();
		}

	}

	private ParseError error(Token token, String errorMsg) {

		Lox.error(token, errorMsg);
		return new ParseError();
	}

	private boolean match(TokenType... tokenTypes) {
		for (final TokenType type : tokenTypes)
			if (check(type)) {
				advance();
				return true;
			}
		return false;
	}

	private Token advance() {
		if (!isAtEnd())
			current = current + 1;
		return previous();
	}

	private boolean check(TokenType type) {
		if (isAtEnd())
			return false;
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
