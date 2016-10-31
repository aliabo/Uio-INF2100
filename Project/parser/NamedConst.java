package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class NamedConst extends UnsignedConstant {

	private String name;

	NamedConst(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; NamedConstant
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * Special condition, name; we use package.Scanner.test if nametoken (else testError)
	 * we also need to update, so a call for package.Scanner.readNextToken
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return n  object UnsignedConstant
	 */
	public static UnsignedConstant parse(Scanner s) {
		enterParser("named constant");
		NamedConst n = new NamedConst(s.curLineNum());

		s.test(nameToken);
		n.name = s.curToken.id;
		s.readNextToken();

		leaveParser("named constant");
		return n;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint(name);
	}

	// Check name of const, set as boolean, else, find right decl and set right type
	@Override void check(Block curScope, Library lib){
		if (name.equals("true") || name.equals("false"))
			type = lib.booleanType;
		else{
			PascalDecl d = curScope.findDecl(name, this);
			type = d.type;
		}
	}

	@Override public String identify() {
		return "<named constant> on line " + lineNum;
	}
}
