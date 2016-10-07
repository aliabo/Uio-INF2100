package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//<term-operator> ::= '+' | '-' | 'or'
public class TermOperator extends Operator {

	String k;

	TermOperator(int lNum){
 		super(lNum);
        }

	@Override public String identify() {
		return "<term opr> on line " + lineNum;
	}

	public static TermOperator parse(Scanner s) {
		enterParser("term opr");
                TermOperator to = new TermOperator(s.curLineNum());

 		if(s.curToken.kind == addToken){
			to.k = "+";
			s.skip(addToken);
		
		}else if (s.curToken.kind == subtractToken){
			to.k = "-";
			s.skip(subtractToken);
		}
		else{
			to.k = "or";
			s.skip(orToken);
		} 
		leaveParser("term opr");
		return to;
	}
       
        @Override void prettyPrint(){
                Main.log.prettyPrint(" " + k + " ");
	}
}
