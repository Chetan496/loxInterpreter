package parser;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import lexer.Token;
import lexer.TokenType;

class TestAstPrinter {

	@Test
	void test() {
		final Unary unary = new Unary(new Token(TokenType.MINUS, "-", null, 1),
				new Literal(123));
		final Token operator = new Token(TokenType.STAR, "*", null, 1);
		final Grouping grouping = new Grouping(new Literal(45.67));
		final Expr expr = new Binary(unary, operator, grouping);
		final AstPrinter astPrinter = new AstPrinter();
		final String astAsString = astPrinter.print(expr);

		Assert.assertEquals("(* (- 123) (group 45.67))", astAsString);
	}

}
