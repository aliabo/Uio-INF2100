package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <factor-operator> ::= '*' | 'div' | 'mod' | 'and'
public class FactorOperator extends Operator {

	String k;

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
				f.k = "*";
				s.skip(multiplyToken);break;
			case divToken:
				f.k = "div";
				s.skip(divToken);break;
			case modToken:
				f.k = "mod";
				s.skip(modToken);break;
			case andToken:
				f.k = "and";
				s.skip(andToken);break;
			default:
				s.skip(multiplyToken);
		}
		
		leaveParser("factor-operator");
		return f;
	}

	@Override void prettyPrint() {

		Main.log.prettyPrint(k);
	}
}
