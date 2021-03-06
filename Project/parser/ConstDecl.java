package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ConstDecl extends PascalDecl{

	public Constant con;
	int constVal;

	ConstDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; ConstDecl
	 * This is a non-terminal representing a declaration, a subclass of PascalDecl
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * -- (name) -- ( = ) -- [constant] --  ( ; ) --
	 * Special condition, name; we use package.Scanner.test if nametoken (else testError)
	 * we also need to update, so a call for package.Scanner.readNextToken
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
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
	 * Abstract code beautifiers, inherited from PascalSyntax -- PascalDecl
	 *
	 * Calls the logFile package.main.log.prettyPrint, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	public @Override void prettyPrint() {
		Main.log.prettyPrint(name + " = ");
		con.prettyPrint();
		Main.log.prettyPrintLn(";");
	}

	/**
	 * All names occurrences will be bound to its declaration.
	 * All elements of the syntax tree containing a constant declare an
	 * "int constVal" and this can be calculated by check which could go
	 * through the whole tree.
	 * Set type to constant
	 *
	 */
	@Override void check(Block curScope, Library lib) {
		curScope.addDecl(name, this);
		con.check(curScope, lib);
		type = con.type;
		constVal = con.constVal;
	}

	@Override public String identify() {
		if(lineNum == 0)//defined in the library
			return "<const decl> " + name + " in the Library";
		else
			return "<const decl> " + name + " on line " + lineNum;
	}

	void checkWhetherAssignable(PascalSyntax where){ where.error("You cannot assign to a constant.");}

	void checkWhetherFunction(PascalSyntax where){
		where.error("const is not a function!");
	}

	void checkWhetherProcedure(PascalSyntax where){
		where.error("const is not a procedure!");
	}

	void checkWhetherValue(PascalSyntax where){}

	@Override void genCode(CodeFile f) {
	}
}
