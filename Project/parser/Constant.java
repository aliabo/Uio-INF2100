package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// ? used for 0 or 1
//<Constant> ::= <PrefixOperator>? <UnsignedConstant> 
public class Constant extends PascalSyntax{

	PrefixOperator pOp = null;
	UnsignedConstant uc;

	Constant(int lNum){
 		super(lNum);
        }

	@Override public String identify() {
		return "<constant> on line " + lineNum;
	}

	public static Constant parse(Scanner s) {
		enterParser("constant");
                Constant c = new Constant(s.curLineNum());

 		if(s.curToken.kind == addToken || s.curToken.kind == subtractToken)
   			c.pOp = PrefixOperator.parse(s);
		c.uc = UnsignedConstant.parse(s);
		leaveParser("constant");
		return c;
	}
       
        @Override void prettyPrint(){
                if(pOp != null){
			pOp.prettyPrint();
		}
		uc.prettyPrint();
	}
}
