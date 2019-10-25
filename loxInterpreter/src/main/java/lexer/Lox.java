package lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import parser.AstPrinter;
import parser.Expr;
import parser.ExprEvaluator;
import parser.Parser;

public class Lox {

	static boolean hadError = false;

	public static void main(String[] args) throws IOException {

		if (args.length > 1) {
			System.out.println("Usage: jlox [script]");
			System.exit(64);

		} else if (args.length == 1)
			runFile(args[0]);
		else
			runPrompt();

	}

	private static void run(String source) {
		final Scanner scanner = new Scanner(source);
		final List<Token> tokens = scanner.scanTokens();
		final Parser parser = new Parser(tokens);
		final Expr expr = parser.parse();

		if (hadError)
			return;

		System.out.println(new AstPrinter().print(expr));
		ExprEvaluator evaluator = new ExprEvaluator();
		Object result = evaluator.evaluate(expr);
		System.out.println(result);

	}

	static void error(int line, String message) {

		report(line, "", message);
	}

	public static void error(Token token, String message) {
		if (token.tokenType == TokenType.EOF)
			report(token.line, " at end", message);
		else
			report(token.line, " at '" + token.lexeme + "'", message);
	}

	private static void report(int line, String where, String message) {
		System.err.println("[line " + line + "] Error " + where + ": " + message);
		hadError = true;

	}

	private static void runFile(String sourceFilePath) throws IOException {

		final String source = Files.readString(Path.of(sourceFilePath));
		run(source);
		if (hadError)
			System.exit(65);

	}

	private static void runPrompt() throws IOException {
		final InputStreamReader input = new InputStreamReader(System.in);
		final BufferedReader reader = new BufferedReader(input);

		for (;;) {

			System.out.println(">");
			run(reader.readLine());
			hadError = false;
		}

	}

}
