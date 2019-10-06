package lexer;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TestScanner {

	@Test
	void testTokenGenForSimpleExpr() {
		final String source = "(;)";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();
		Assert.assertEquals(3, scanTokens.size());
		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.LEFT_PAREN, firstToken.tokenType);
		Assert.assertEquals(TokenType.SEMICOLON, secondToken.tokenType);
		Assert.assertEquals(TokenType.RIGHT_PAREN, thirdToken.tokenType);
	}

	@Test
	void testTokenGenForSimpleExprHavingTwoCharLexeme() {
		final String source = "(>=)";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();
		Assert.assertEquals(3, scanTokens.size());
		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.LEFT_PAREN, firstToken.tokenType);
		Assert.assertEquals(TokenType.GREATER_EQUAL, secondToken.tokenType);
		Assert.assertEquals(TokenType.RIGHT_PAREN, thirdToken.tokenType);
	}

	@Test
	void testTokenGenForExprHavingString() {
		final String source = "(\"CHETAN\")";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();
		Assert.assertEquals(3, scanTokens.size());
		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.LEFT_PAREN, firstToken.tokenType);
		Assert.assertEquals(TokenType.STRING, secondToken.tokenType);
		Assert.assertEquals(TokenType.RIGHT_PAREN, thirdToken.tokenType);
	}

	@Test
	void testTokenGenForExprContainingNumber() {
		final String source = "(123)";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();
		Assert.assertEquals(3, scanTokens.size());
		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.LEFT_PAREN, firstToken.tokenType);
		Assert.assertEquals(TokenType.NUMBER, secondToken.tokenType);
		Assert.assertEquals(TokenType.RIGHT_PAREN, thirdToken.tokenType);
	}

	@Test
	void testTokenGenForExprContainingNumberAndOperators() {
		final String source = "(123+24*79)";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();
		Assert.assertEquals(7, scanTokens.size());
		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.LEFT_PAREN, firstToken.tokenType);
		Assert.assertEquals(TokenType.NUMBER, secondToken.tokenType);
		Assert.assertEquals(TokenType.PLUS, thirdToken.tokenType);
	}

	@Test
	void testTokenGenForExprContainingFloatNumberAndOperators() {
		final String source = "(123+24.67*79)";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();
		Assert.assertEquals(7, scanTokens.size());
		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.LEFT_PAREN, firstToken.tokenType);
		Assert.assertEquals(TokenType.NUMBER, secondToken.tokenType);
		Assert.assertEquals(TokenType.PLUS, thirdToken.tokenType);
	}

	@Test
	void testTokenGenForStmtContainingIdentifier() {
		final String source = "var answer = (123+24.67*79)";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();

		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.VAR, firstToken.tokenType);
		Assert.assertEquals(TokenType.IDENTIFIER, secondToken.tokenType);
		Assert.assertEquals(TokenType.EQUAL, thirdToken.tokenType);
	}

	@Test
	void testTokenGenForSingleLineComment() {
		final String source = "var x; //var answer = (123+24.67*79)";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();

		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.VAR, firstToken.tokenType);
		Assert.assertEquals(TokenType.IDENTIFIER, secondToken.tokenType);
		Assert.assertEquals(TokenType.SEMICOLON, thirdToken.tokenType);
	}

	@Test
	void testTokenGenForMultiLineComment() {
		final String source = "var x = answer; /*mult\r\n" + "line \r\n" + "comment*/";
		final Scanner scanner = new Scanner(source);
		final List<Token> scanTokens = scanner.scanTokens();

		final Token firstToken = scanTokens.get(0);
		final Token secondToken = scanTokens.get(1);
		final Token thirdToken = scanTokens.get(2);
		Assert.assertEquals(TokenType.VAR, firstToken.tokenType);
		Assert.assertEquals(TokenType.IDENTIFIER, secondToken.tokenType);
		Assert.assertEquals(TokenType.EQUAL, thirdToken.tokenType);
	}
}
