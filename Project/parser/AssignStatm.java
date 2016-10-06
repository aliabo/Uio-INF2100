package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// --> [variable] --> ( := ) --> [expression]
class AssignStatm extends Statement {

    private Variable var = null;
    private Expression ex = null;


    AssignStatm(int lNum) {
        super(lNum);
    }

    public static AssignStatm parse(Scanner s) {
        enterParser("assignstatm");
        AssignStatm as = new AssignStatm(s.curLineNum());

        as.var = new Variable.parse(s);
        s.skip(assignToken);
        as.ex = new Expression.parse(s);

        return as;
    }

    @Override
    public String identify() {
        return "<assignstatm> on line " + lineNum;
    }

    @Override void prettyPrint() {
        var.prettyprint();
        main.Main.log.prettyPrint(" :=");
        ex.prettyPrint();
    }
}
