package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class NumberLiteral extends UnsignedConstant {

	String num;

	NumberLiteral(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<number-literal> on line " + lineNum;
	}

	public static NumberLiteral parse(Scanner s) {
         
		enterParser("number-literal");
		NumberLiteral n = new NumberLiteral(s.curLineNum());
		s.test(intValToken);
		n.num = s.curToken.id;
		s.readNextToken();
		leaveParser("number-literal");
		return n;
	}

	@Override void prettyPrint() {
	
		Main.log.prettyPrint(num);		
	}
}
