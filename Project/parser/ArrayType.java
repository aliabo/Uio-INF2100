package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ArrayType extends Type{

	private Constant c1;
	private Constant c2;
	private Type type;


	ArrayType(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; array-type
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * --> (array) --> ( [ ) --> [constant] --> ( .. ) --> [constant] --> ( ] ) --> (of) --> [type]
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(non-terminal), send it to specific parser [terminal]
	 *
	 * @return a  object ArrayType
	 */
	public static ArrayType parse(Scanner s) {

		enterParser("array type");
		ArrayType a = new ArrayType(s.curLineNum());
		s.skip(arrayToken);
		s.skip(leftBracketToken);
		a.c1 = Constant.parse(s);
		s.skip(rangeToken);
		a.c2 = Constant.parse(s);
		s.skip(rightBracketToken);
		s.skip(ofToken);
		a.type = Type.parse(s);
		leaveParser("array type");
		return a;
	}

	/**
	 * Abstract code beautifiers, inherited from PascalSyntax --> Type
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	@Override void prettyPrint() {
		Main.log.prettyPrint("array [");
		c1.prettyPrint();
		Main.log.prettyPrint("..");
		c2.prettyPrint();
		Main.log.prettyPrint("] of");
		type.prettyPrint();
		Main.log.prettyPrintLn("");
	}

	@Override public String identify() {
		return "<array type> on line " + lineNum;
	}
}
