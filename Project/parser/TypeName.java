package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <type-name> ::= <name>
public class TypeName extends Type{

	String name;

	TypeName(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<type-name> on line " + lineNum;
	}

	public static TypeName parse(Scanner s) {
         
		enterParser("type-name");
		TypeName t = new TypeName(s.curLineNum());
		s.test(nameToken);
		t.name = s.curToken.id;
		s.readNextToken();
		leaveParser("type-name");
		return t;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint(name);
	}
}
