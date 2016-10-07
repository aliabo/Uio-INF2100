package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//TODO implement abstract from ParscalDecl
public class ConstDecl extends PascalDecl{

	private Constant con;

	ConstDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; array-type
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
	 *              s.skip(non-terminal), send it to specific parser [terminal]
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

	@Override public String identify() {
		return "<const decl> " + name + " on line " + lineNum;
	}

	//abstract void checkWhetherAssignable(PascalSyntax where);
	//abstract void checkWhetherFunction(PascalSyntax where);
	//abstract void checkWhetherProcedure(PascalSyntax where);
	//abstract void checkWhetherValue(PascalSyntax where);
}
