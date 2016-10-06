package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//<prefix-operator> ::= '+' | '-' 
public class PrefixOperator extends Operator {

	String k;

	PrefixOperator(int lNum){
 		super(lNum);
        }

	@Override public String identify() {
		return "<prefix-operator> on line " + lineNum;
	}

	public static PrefixOperator parse(Scanner s) {
		enterParser("prefix-operator");
                PrefixOperator p = new PrefixOperator(s.curLineNum());

 		if(s.curToken.kind == addToken){
			p.k = "+";
			s.skip(addToken);
		}
                else{
			p.k = "-";
			s.skip(subtractToken);
		} 
		leaveParser("prefix-operator");
		return p;
	}
       
        @Override void prettyPrint(){
                Main.log.prettyPrint(k);
	}

}
