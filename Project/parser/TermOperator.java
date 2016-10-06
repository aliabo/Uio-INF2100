package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//<term-operator> ::= '+' | '-' | 'or'
public class TermOperator extends Operator {

	TokenKind k;

	TermOperator(int lNum){
 		super(lNum);
        }

	@Override public String identify() {
		return "<term-operator> on line " + lineNum;
	}

	public static TermOperator parse(Scanner s) {
		enterParser("term-operator");
                TermOperator to = new TermOperator(s.curLineNum());

 		if(s.curToken.kind == addToken)
			to.k = addToken;
			s.skip(addToken);
                else if (s.curToken.kind == subtractToken){
			to.k = subtractToken;
			s.skip(subtractToken);
		}
		else{
			to.k = orToken;
			s.skip(orToken);
		} 
		leaveParser("term-operator");
		return to;
	}
       
        @Override void prettyPrint(){
                prettyPrint(k);
	}
}
