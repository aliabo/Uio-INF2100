package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Negation extends Factor {

	private Factor f;

	Negation(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; negation
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * -- (not) -- [factor] --
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return n  object Negation
	 */
	public static Negation parse(Scanner s) {

		enterParser("negation");
		Negation n = new Negation(s.curLineNum());

		s.skip(notToken);
		n.f = Factor.parse(s);

		leaveParser("negation");
		return n;
	}

	// check recursively type, set type
	@Override void check(Block curScope, Library lib){
		f.check(curScope,lib);
		type = f.type;
		type.checkType(lib.booleanType, "'not' operand", this,
                       "not and"+ f.identify() +" are of different type!");
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint("not ");
		f.prettyPrint();
	}

	@Override public String identify() {
		return "<negation> on line " + lineNum;
	}

	@Override void genCode(CodeFile c) {
		f.genCode(c);
		c.genInstr("","xorl", "$0x1,%eax", "  not");
	}
}
