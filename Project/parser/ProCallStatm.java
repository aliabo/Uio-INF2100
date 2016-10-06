package parser;
import scanner.Scanner;

import static scanner.TokenKind.*;

// ? = 0 or 1
// --> [name] --> ? ( ( ) --> [expression] --> ( , )
public class ProCallStatm extends Statement {
    ProCallStatm(int lNum) {
        super(lNum);
    }

    Expression ex = null;


    @Override public String identify() {
        return "<procallstatm> on line " + lineNum;
    }

    public static ProCallStatm parse(Scanner s) {
        enterParser("procallstatm");
        s.test(nameToken);
        ProCallStatm pc = new ProCallStatm(s.curLineNum());
        s.readNextToken();

        if(s.curToken.kind == leftBracketToken) {
            s.skip(leftBracketToken);
            pc.ex = Expression.parse(s);

            while(s.curToken.kind == commaToken){
                s.skip(commaToken);
                pc.ex = Expression.parse(s);
            }
            s.skip(rightBracketToken;
        }
        leaveParser("procallstatm");
        return pc;
    }
}
