package parser;
import main.Main;
import scanner.Scanner;
import java.util.ArrayList;
import static scanner.TokenKind.*;

// ? = 0 or 1
// --> [name] --> ? ( ( ) --> [expression] --> ( , )
class ProCallStatm extends Statement {

    ProCallStatm(int lNum) {
        super(lNum);
    }

    private ArrayList<Expression> exList = new ArrayList<>();
    private TypeName name;


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
            pc.exList.add(Expression.parse(s));

            while(s.curToken.kind == commaToken){
                s.skip(commaToken);
                pc.exList.add(Expression.parse(s));
            }
            s.skip(rightBracketToken);
        }
        //pc.name = TypeName.parse(s);
        leaveParser("procallstatm");
        return pc;
    }

    @Override void prettyPrint() {
        Main.log.prettyPrint(name + " ");
        if (exList != null) {
            //Main.log.prettyPrint(" (");
            exList.get(0).prettyPrint();
            exList.remove(0);

            if(exList.size() == 0){
                //print a new line
                Main.log.prettyPrintLn("");
            }

            for(Expression ex: exList){
                Main.log.prettyPrintLn(",");
                ex.prettyPrint();
            }
            //Main.log.prettyPrint(") ");
        }
    }
}
