package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class VarDecl extends PascalDecl{

	private Type type;

	VarDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Var decl
     * This is a non-terminal representing a declaration, a subclass of PascalDecl
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * --> [name] -->  ( : ) --> [type] --> ( ; ) -->
	 * [name], we have a special condition, need to test before we create object
     * readNextToken()
	 * s.skip() parse() s.skip()
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return vDecl  object VArDecl
	 */
	public static VarDecl parse(Scanner s) {
		enterParser("var decl");
		s.test(nameToken);

		VarDecl vDecl = new VarDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();
		s.skip(colonToken);
		vDecl.type = Type.parse(s);
		s.skip(semicolonToken);

		leaveParser("var decl");
		return vDecl;
	}

	public @Override void prettyPrint() {
		Main.log.prettyPrint(name + ": ");
		type.prettyPrint();
		Main.log.prettyPrintLn(";");
	}

	@Override public String identify() {
		return "<var decl> " + name + " on line " + lineNum;
	}

	void checkWhetherAssignable(PascalSyntax where){}

	void checkWhetherFunction(PascalSyntax where){
		where.error("Variable is not a function!");
	}

	void checkWhetherProcedure(PascalSyntax where){
		where.error("Variable is not a procedure!");
	}

	void checkWhetherValue(PascalSyntax where){}
}
