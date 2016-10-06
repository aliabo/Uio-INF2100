package parser;
import main.Main;
import scanner.Scanner;
import java.util.ArrayList;
import static scanner.TokenKind.*;

// ? = 0 or 1
// --> [name] --> ? ( ( ) --> [expression] --> ( , )
class ProcCallStatm extends Statement {

    ProcCallStatm(int lNum) {
        super(lNum);
    }

    private ArrayList<Expression> exList = new ArrayList<>();
    private TypeName name;


    @Override public String identify() {
        return "<proc-call-statm> on line " + lineNum;
    }

    public static ProcCallStatm parse(Scanner s) {
        enterParser("proc-call-statm");
        s.test(nameToken);
        ProcCallStatm pc = new ProcCallStatm(s.curLineNum());
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
        leaveParser("proc-call-statm");
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
