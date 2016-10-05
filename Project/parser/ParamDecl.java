package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <param-decl> ::= <name> ':' <type-name>
public class ParamDecl extends PascalDecl{

	TypeName name;

	ParamDecl(String id, int lNum) {
		super(id, lNum);
	}

	@Override public String identify() {
		return "<param-decl> " + name + " on line " + lineNum;
	}

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
}
