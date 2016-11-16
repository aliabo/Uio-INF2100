package parser;
import scanner.*;
import main.*;
public class EmptyStatm extends Statement{

	EmptyStatm(int lNum){
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Empty statm
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * --
	 * This sentence does nothing; it exists only so that it should be allowed to have extra semicolon.
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return a  object EmptyStatm
	 */
	public static EmptyStatm parse(Scanner s) {
		enterParser("empty statm");

		leaveParser("empty statm");
		return new EmptyStatm(s.curLineNum());
	}

	@Override void check(Block curScope, Library lib) {}

	@Override void prettyPrint() {}

	@Override public String identify() {
		return "<empty statm> on line " + lineNum;
	}

	@Override void genCode(CodeFile f) {}
}
