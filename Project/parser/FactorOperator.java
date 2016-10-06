package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <factor-operator> ::= '*' | 'div' | 'mod' | 'and'
public class FactorOperator extends Operator {

	TokenKind k;

	FactorOperator(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<factor-operator> on line " + lineNum;
	}

	public static FactorOperator parse(Scanner s) {
         
		enterParser("factor-operator");
		FactorOperator f = new FactorOperator(s.curLineNum());
		
		switch(s.curToken.kind){
			case multiplyToken:
				f.k = multiplyToken;
				s.skip(multiplyToken);break;
			case divToken:
				f.k = divToken;
				s.skip(divToken);break;
			case modToken:
				f.k = modToken;
				s.skip(modToken);break;
			case andToken:
				f.k = andToken;
				s.skip(andToken);break;
			default:
				s.skip(multiplyToken);
		}
		
		leaveParser("factor-operator");
		return se;
	}

	@Override void prettyPrint() {

		Main.log.prettyPrint(k);
	}
}
