package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ParamDecl extends PascalDecl{

	private String name;
	private TypeName tName;

	ParamDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Param Decl
	 * functions always return a value
	 * This is a non-terminal representing a declaration, a subclass of PascalDecl
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * -- [name] -- ( : ) -- [type-name]
	 *
	 * Special condition, name; we use package.Scanner.test if nametoken (else testError)
	 * we also need to update, so a call for package.Scanner.readNextToken
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return p  object ParamDecl
	 */
	public static ParamDecl parse(Scanner s) {
		enterParser("param decl");
		s.test(nameToken);
		ParamDecl p = new ParamDecl(s.curToken.id, s.curLineNum());
		p.name = s.curToken.id;
		s.readNextToken();

		s.skip(colonToken);
		p.tName = TypeName.parse(s);

		leaveParser("param decl");
		return p;
	}

	public @Override void prettyPrint() {
		Main.log.prettyPrint(name + ": ");
		tName.prettyPrint();
	}

	@Override public String identify() {
		return "<param decl> " + name + " on line " + lineNum;
	}

	// name, we add to decl list(if not already in scoop) check, and set type
	@Override void check(Block curScope, Library lib) {
		curScope.addDecl(name, this);
		tName.check(curScope, lib);
		type = tName.type;
	}

	// left: it is assignable, right: value
	void checkWhetherAssignable(PascalSyntax where){}
	void checkWhetherFunction(PascalSyntax where){
		where.error("parameter is not a function!");
	}

	void checkWhetherProcedure(PascalSyntax where){
		where.error("parameter is not a procedure!");
	}
	void checkWhetherValue(PascalSyntax where){}

	@Override void genCode(CodeFile f) {}
}
