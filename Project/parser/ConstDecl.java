package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ConstDecl extends PascalDecl{

	private Constant con;
	int constVal;

	ConstDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; ConstDecl
	 * This is a non-terminal representing a declaration, a subclass of PascalDecl
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * --> (name) -->  ( = ) --> [constant] -->  ( ; ) -->
	 * Special condition, name; we use {@link package.test} if nametoken (else testError)
	 * we also need to update, so a call for {@link package.readNextToken}
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return cDecl  object ConstDecl
	 */
	public static ConstDecl parse(Scanner s) {
		enterParser("const decl");
		s.test(nameToken);
		ConstDecl cDecl = new ConstDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();
		s.skip(equalToken);
		cDecl.con = Constant.parse(s);
		s.skip(semicolonToken);

		leaveParser("const decl");
		return cDecl;
	}

	/**
	 * Abstract code beautifiers, inherited from PascalSyntax --> PascalDecl
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	public @Override void prettyPrint() {
		Main.log.prettyPrint(name + " = ");
		con.prettyPrint();
		Main.log.prettyPrintLn(";");
	}

	@Override void check(Block curScope, Library lib) {
		curScope.addDecl(name, this);
	}

	@Override public String identify() {
		if(lineNum == 0)//defined in the library
			return "<const decl> " + name + " in the Library";
        	else
			return "<const decl> " + name + " on line " + lineNum;
	}

	void checkWhetherAssignable(PascalSyntax where){ where.error("const is not assignable!");}

	void checkWhetherFunction(PascalSyntax where){
		where.error("const is not a function!");
	}

	void checkWhetherProcedure(PascalSyntax where){
		where.error("const is not a procedure!");
	}

	void checkWhetherValue(PascalSyntax where){}
}
