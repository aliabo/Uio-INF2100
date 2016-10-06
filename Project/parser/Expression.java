package parser;
import scanner.Scanner;

// ? = 0 or 1
// --> [Simple expres] --> ? [rel opr] --> [simple expres]
public class Expression extends PascalSyntax {

    Expression(int lNum) {
        super(lNum);
    }

    //SimpleExpr sE1 = null;
    //SimpleExpr sE2 = null;
    //RelOperator rO = null;

    @Override public String identify() {
        return "<expression> on line " + lineNum;
    }

    public static Expression parse(Scanner s) {
        enterParser("Expression");

        Expression ex = new Expression(s.curLineNum());
        //ex.sE1 = SimpleExpr.parse(s);

        if(s.curToken.kind == elseToken) {
            s.skip(elseToken);
            ifS.stat2 = Statement.parse(s);
        }

        leaveParser("expression");
        return ex;
    }

    void prettyPrint(){

    }
}
