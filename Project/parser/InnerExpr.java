package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <inner-expr> ::= '(' <expression> ')'
public class InnerExpr extends Factor {

	Expression exp;


	InnerExpr(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<inner-expr> on line " + lineNum;
	}

	public static InnerExpr parse(Scanner s) {
         
		enterParser("inner-expr");
		InnerExpr iExp = new InnerExpr(s.curLineNum());
			
		s.skip(leftParToken);
		iExp.exp = Expression.parse(s);
		s.skip(rightParToken);
			
		leaveParser("inner-expr");
		return iExp;
	}

	@Override void prettyPrint() {
		
		Main.log.prettyPrint("(");
		exp.prettyPrint();
		Main.log.prettyPrint(")");
	}
}
