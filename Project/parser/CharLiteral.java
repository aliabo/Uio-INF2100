package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class CharLiteral extends UnsignedConstant {

	private char c;

	CharLiteral(int lNum) {
		super(lNum);
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

	@Override public String identify() {
		return "<char-literal> on line " + lineNum;
	}

}
