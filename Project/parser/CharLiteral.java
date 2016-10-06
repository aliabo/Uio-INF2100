package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class CharLiteral extends UnsignedConstant {

	char c;

	CharLiteral(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<char-literal> on line " + lineNum;
	}

	public static CharLiteral parse(Scanner s) {
         
		enterParser("char-literal");
		CharLiteral cl = new CharLiteral(s.curLineNum());
		s.test(charValToken);
		cl.c = s.curToken.id.charAt(0);
		s.readNextToken();
		leaveParser("char-literal");
		return cl;
	}

	@Override void prettyPrint() {
	
		Main.log.prettyPrint("'" + c + "'");		
	}

}
