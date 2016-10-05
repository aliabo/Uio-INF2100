package parser;

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
		cl.c = s.curToken;
		s.readNextToken();
		leaveParser("char-literal");
		return n;
	}

	@Override void prettyPrint() {
	
		Main.log.prettyPrint("'" + c + "'");		
	}

}
