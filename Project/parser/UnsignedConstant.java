package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//<unsigned-const> ::= <named-const> | <number-literal> | <char-literal>
public abstract class UnsignedConstant extends Factor {
	

	UnsignedConstant(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<unsigned constant> on line " + lineNum;
	}

	public static UnsignedConstant parse(Scanner s) {
         
		enterParser("unsigned constant");
		UnsignedConstant uc = null;
		switch (s.curToken.kind) {
			case nameToken:
				uc = NamedConst.parse(s); break;
			case intValToken:
				uc = NumberLiteral.parse(s); break;
			case charValToken:
				uc = CharLiteral.parse(s); break;
			default:
				s.testError("nameToken");
		}
		leaveParser("unsigned constant");
		return uc;
	}

	@Override void prettyPrint() {
		
	}
}
