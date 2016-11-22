package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class VarDecl extends PascalDecl{

	public Type t;
	public int offset;
	VarDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Var decl
     * This is a non-terminal representing a declaration, a subclass of PascalDecl
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * -- [name] --  ( : ) -- [type] -- ( ; ) --
	 * [name], we have a special condition, need to test before we create object
     * readNextToken()
	 * s.skip() parse() s.skip()
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return vDecl  object VArDecl
	 */
	public static VarDecl parse(Scanner s) {
		enterParser("var decl");
		//s.test(nameToken);

		VarDecl vDecl = new VarDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();
		s.skip(colonToken);
		vDecl.t = Type.parse(s);
		s.skip(semicolonToken);

		leaveParser("var decl");
		return vDecl;
	}

	public @Override void prettyPrint() {
		Main.log.prettyPrint(name + ": ");
		t.prettyPrint();
		Main.log.prettyPrintLn(";");
	}

	@Override public String identify() {
		return "<var decl> " + name + " on line " + lineNum;
	}

	/**
	 * 	Name --> addDecl this.
	 * 	check type
	 * 	set type
	 *
	 * 	@param curScope		current block
	 * 	@param lib 			library (bind)
 	 */

	@Override void check(Block curScope, Library lib) {
		curScope.addDecl(name, this);
		t.check(curScope, lib);
		type = t.type;
		declLevel = curScope.level;
	}

	/**
	 *  In any declarations that may be left in an assignment void checkWhetherAssignable(PascalSyntax where) is left blank
	 *
	 * @param where		indicating where in your program type occurs
	 */
	void checkWhetherAssignable(PascalSyntax where){}

	void checkWhetherFunction(PascalSyntax where){
		where.error("Variable is not a function!");
	}

	void checkWhetherProcedure(PascalSyntax where){
		where.error("Variable is not a procedure!");
	}

	void checkWhetherValue(PascalSyntax where){}

	@Override void genCode(CodeFile f) {
		//t.genCode(f);
	}
}
