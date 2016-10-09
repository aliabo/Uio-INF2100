package parser;
import scanner.*;

abstract class Statement extends PascalSyntax {


	Statement(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Statement
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
	 * --> ? [assign statm] --> ? [compound stat] --> ? [empty statm] --> ? [ if-statm] --> ? [proc call] --> ?  [while-statm] -->
	 *
	 * If a sentence starts with a NAMETOKEN; it can either be [assign statm], [proc call]
	 * By looking at the next symbol;
	 * ( := )  	Is a ⟨assign statm⟩
	 * ( [  )  	Is also a ⟨assign statm⟩
	 * ( (  ) 	A ⟨proc call⟩
	 * ( ;  )	is a ⟨proc call⟩ (without parameters), end is also a ⟨proc call⟩
	 *
	 * Semicolons is used to separate statements, so the last sentence before an end shall have no semicolon.
	 * (But if you put a semicolon there anyway, it just means that there is an extra empty sentence before,
	 * end and it does not do anything for program execution.
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return st  object Statement
	 */
	public static Statement parse(Scanner s) {

		enterParser("statement");
		Statement st = null;
		switch (s.curToken.kind) {
			case beginToken:
				st = CompoundStatm.parse(s); break;
			case ifToken:
				st = IfStatm.parse(s); break;
			case nameToken:
				//TODO check!!!
				switch (s.nextToken.kind) {
					case assignToken:
						st = AssignStatm.parse(s); break;
					case leftBracketToken:
						st = AssignStatm.parse(s); break;

					default:
						st = ProcCallStatm.parse(s);
						leaveParser("statement");
						return st;
				} break;
			case whileToken:
				st = WhileStatm.parse(s); break;
			default:
				st = EmptyStatm.parse(s); break;
		}
		leaveParser("statement");
		return st;
	}

	@Override public String identify() {
		return "<statement> on line " + lineNum;
	}

	@Override void prettyPrint() {

	}


}
