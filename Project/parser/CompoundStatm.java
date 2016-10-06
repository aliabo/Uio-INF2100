package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// --> (Begin) --> [Statmlist] --> (End)
class CompoundStatm extends Statement{

    CompoundStatm(int lNum) {
        super(lNum);
    }

    private StatmList st;

    public static Statement parse(Scanner s) {
        enterParser("compundStatm");
        CompoundStatm cs = new CompoundStatm(s.curLineNum());

        s.skip(beginToken);
        cs.st = StatmList.parse(s);
        s.skip(endToken);

        leaveParser("compundStatm");
        return cs;
    }

    @Override public String identify() {
        return "<compundstatm> on line " + lineNum;
    }

    @Override void prettyPrint() {
        Main.log.prettyPrint("begin ");
        st.prettyPrint();
        Main.log.prettyPrintLn(" end");
        Main.log.prettyIndent();
    }
}
