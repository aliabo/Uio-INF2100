package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// ? == 0 or 1
// (if) --> [expression] --> (then) --> [statment] --> ? (else) --> [statment] -->

public class IfStatm extends Statement {

    Statement stat1 = null;
    Statement stat2 = null;
    Expression exp = null;

    IfStatm(int lNum) {
        super(lNum);
    }

    public static IfStatm parse(Scanner s) {
        enterParser("IfStatm");

        IfStatm ifS = new IfStatm(s.curLineNum());
        s.skip(ifToken);
        ifS.exp = Expression.parse(s);

        s.skip(thenToken);
        ifS.stat1 = Statement.parse(s);

        if(s.curToken.kind == elseToken) {
            s.skip(elseToken);
            ifS.stat2 = Statement.parse(s);
        }

        leaveParser("IfStatm");
        return ifS;
    }

    @Override public String identify() {
        return "<ifstatm> on line " + lineNum;
    }

    @Override void prettyPrint(){
        Main.log.prettyPrint("If ");
        //exp.prettyPrint()
        Main.log.prettyPrint(" then");
        stat1.prettyPrint();

        if(stat2 != null){
            Main.log.prettyPrint(" else");
            stat2.prettyPrint();
        }
    }
}
