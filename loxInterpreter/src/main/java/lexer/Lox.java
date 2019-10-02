package lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

		for (final Token token : tokens)
			System.out.println(token);

	}

	static void error(int line, String message) {

		report(line, "", message);
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
