package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Program extends PascalDecl {

	private Block progBlock;

	Program(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Program
	 * Pascal 2016 program simply an [block[ with a name
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * --> [program] --> [name] --> ( ; ) [block] ( . ) -->
	 *
	 * Special condition, name; we use {@link package.test} if nametoken (else testError)
	 * we also need to update, so a call for {@link package.readNextToken}
	 * s.skip(), [non-terminal] and new [block]
	 * If we reach an . were in end of file
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return p  object Program
	 */
	public static Program parse(Scanner s) {
		enterParser("program");

		s.skip(programToken);
		s.test(nameToken);
		Program p = new Program(s.curToken.id, s.curLineNum());
		s.readNextToken();

		s.skip(semicolonToken);
		p.progBlock = Block.parse(s);
		p.progBlock.context = p;

		if(s.curToken.kind != dotToken)
			Main.error(-1,"Expected a . but found a e-o-f!");
		else{
			s.readNextToken();
		}
		leaveParser("program");
		return p;
	}

	public @Override void prettyPrint() {
		Main.log.prettyPrintLn("program " + name + ";");
		progBlock.prettyPrint();

		Main.log.prettyPrint(".");
	}

	@Override public String identify() {
		return "<program> " + name + " on line " + lineNum;
	}

	void checkWhetherAssignable(PascalSyntax where){
		where.error("program is not assignable!");
	}

	void checkWhetherFunction(PascalSyntax where){
		where.error("program is not a function!");
	}

	void checkWhetherProcedure(PascalSyntax where){
		where.error("program is not a procedure!");
	}

	void checkWhetherValue(PascalSyntax where){
		where.error("program is not a value!");
	}
}
