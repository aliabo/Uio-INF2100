package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class NumberLiteral extends UnsignedConstant {

	private int num;

	NumberLiteral(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; NumberLiteral
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'One' == 1, in combination with '*' and '?'
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 *
	 * --> One [digit 0 - 9] * -->
	 * Special condition, intValToken; we use {@link package.test} if intValToken (else testError)
	 * we also need to update, so a call for {@link package.readNextToken}
	 * Assign intVal
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return n  object NumberLiteral
	 */
	public static NumberLiteral parse(Scanner s) {
		enterParser("number literal");
		NumberLiteral n = new NumberLiteral(s.curLineNum());

		s.test(intValToken);
		n.num = s.curToken.intVal;
		s.readNextToken();

		leaveParser("number literal");
		return n;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint(num + "");
	}

	@Override public String identify() {
		return "<number literal> on line " + lineNum;
	}
}
