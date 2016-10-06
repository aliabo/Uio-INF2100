package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class NamedConst extends UnsignedConstant {

	String name;

	NamedConst(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<named-const> on line " + lineNum;
	}

	public static UnsignedConstant parse(Scanner s) {
         
		enterParser("named-const");
		NamedConst n = new NamedConst(s.curLineNum());
		s.test(nameToken);
		n.name = s.curToken.id;
		s.readNextToken();
		leaveParser("named-const");
		return n;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint(name);
	}

}
