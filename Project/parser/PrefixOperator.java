package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//<prefix-operator> ::= '+' | '-' 
public class PrefixOperator extends Operator {

	TokenKind k;

	PrefixOperator(int lNum){
 		super(lNum);
        }

	@Override public String identify() {
		return "<prefix-operator> on line " + lineNum;
	}

	public static PrefixOperator parse(Scanner s) {
		enterParser("prefix-operator");
                PrefixOperator p = new PrefixOperator(s.curLineNum());

 		if(s.curToken.kind == addToken)
			p.k = addToken;
			s.skip(addToken);
                else{
			p.k = subtractToken;
			s.skip(subtractToken)
		} 
		leaveParser("prefix-operator");
		return p;
	}
       
        @Override void prettyPrint(){
                prettyPrint(k);
	}

}
