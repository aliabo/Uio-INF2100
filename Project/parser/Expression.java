package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// ? = 0 or 1
// --> [Simple expres] --> ?( [rel opr] --> [simple expres])
public class Expression extends PascalSyntax {

    Expression(int lNum) {
        super(lNum);
    }

    SimpleExpr sE1 = null;
    SimpleExpr sE2 = null;
    RelOperator rO = null;

    @Override public String identify() {
        return "<expression> on line " + lineNum;
    }

    public static Expression parse(Scanner s) {
        enterParser("Expression");

        Expression ex = new Expression(s.curLineNum());
        ex.sE1 = SimpleExpr.parse(s);

        if(s.curToken.kind == equalToken || s.curToken.kind == notEqualToken ||
	   s.curToken.kind == lessToken ||s.curToken.kind == lessEqualToken ||
	   s.curToken.kind == greaterToken ||s.curToken.kind == greaterEqualToken) {
            
		ex.rO = RelOperator.parse(s);
		ex.sE2 = SimpleExpr.parse(s);
        }

        leaveParser("expression");
        return ex;
    }

    void prettyPrint(){
	sE1.prettyPrint();
	if(rO != null){
		rO.prettyPrint();
		sE2.prettyPrint();
	}
    }
}
