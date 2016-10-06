package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ParamDecl extends PascalDecl{

	private TypeName name;

	ParamDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Param Decl
	 * functions always return a value
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * --> [name] --> ( : ) --> [type-name]
	 *
	 * Special condition, name; we use {@link package.test} if nametoken (else testError)
	 * we also need to update, so a call for {@link package.readNextToken}
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(non-terminal), send it to specific parser [terminal]
	 *
	 * @return p  object ParamDecl
	 */
	public static ParamDecl parse(Scanner s) {
		enterParser("param-decl");
		s.test(nameToken);
		ParamDecl p = new ParamDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();

		s.skip(colonToken);
		p.name = TypeName.parse(s);

		leaveParser("param-decl");
		return p;
	}

	public @Override void prettyPrint() {
		Main.log.prettyPrint(name + " : ");
		name.prettyPrint();
	}

	//abstract void checkWhetherAssignable(PascalSyntax where);
	//abstract void checkWhetherFunction(PascalSyntax where);
	//abstract void checkWhetherProcedure(PascalSyntax where);
	//abstract void checkWhetherValue(PascalSyntax where);

	@Override public String identify() {
		return "<param-decl> " + name + " on line " + lineNum;
	}
}
