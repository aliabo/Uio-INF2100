package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class WhileStatm extends Statement {

	private Expression expr;
	private Statement body;

	WhileStatm(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; WhileStatm
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * -- ( while ) -- [expression] -- ( do ) --> [statement] --
	 * These phrases behave like we are used to:
	 * s.skip() parse()
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return iExp  object WhileStatm
	 */
	public static WhileStatm parse(Scanner s) {
		enterParser("while-statm");
		WhileStatm ws = new WhileStatm(s.curLineNum());

		s.skip(whileToken);
		ws.expr = Expression.parse(s);
		s.skip(doToken);
		ws.body = Statement.parse(s);

		leaveParser("while-statm");
		return ws;
	}

  @Override void prettyPrint() {
		Main.log.prettyPrint("while ");
		expr.prettyPrint();
		Main.log.prettyPrintLn(" do");
		Main.log.prettyIndent();
		body.prettyPrint();
		Main.log.prettyOutdent();
	}

	@Override void genCode(CodeFile f) {
		String testLabel = f.getLocalLabel(),
		endLabel = f.getLocalLabel();
		f.genInstr(testLabel, "", "", "Start while-statement");
		expr.genCode(f);
		f.genInstr("", "cmpl", "$0,%eax", "");
		f.genInstr("", "je", endLabel, "");
		body.genCode(f);
		f.genInstr("", "jmp", testLabel, "");
		f.genInstr(endLabel, "", "", "End while-statement");
	}

	@Override void check(Block curScope, Library lib) {
		expr.check(curScope, lib);
		expr.type.checkType(lib.booleanType, "while-test", this,
				"While-test is not Boolean.");
		body.check(curScope, lib);
	}

	@Override public String identify() {
		return "<while-statm> on line " + lineNum;
	}
}
