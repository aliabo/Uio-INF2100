package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

// + one or more
// <statm-list> ::= <statement> (';' <statement>)*
public class StatmList extends PascalSyntax{

    ArrayList<Statement> stList = new ArrayList<>();


    StatmList(int lNum){
        super(lNum);
    }

    @Override public String identify() {
        return "<statm-list> on line " + lineNum;
    }

    public static StatmList parse(Scanner s) {
        enterParser("statm-list");
        StatmList sl = new StatmList(s.curLineNum());

        sl.stList.add(Statement.parse(s));
        while(s.curToken.kind == semicolonToken){

            s.skip(semicolonToken);
            sl.stList.add(Statement.parse(s));
        }

        leaveParser("statm-list");
        return sl;
    }

    @Override void prettyPrint(){

        stList.get(0).prettyPrint();
        stList.remove(0);

        if(stList.size() == 0){
            //print a new line
            Main.log.prettyPrintLn("");
        }

        for(Statement st: stList ){
            Main.log.prettyPrintLn(";");
            st.prettyPrint();
        }
    }

}
