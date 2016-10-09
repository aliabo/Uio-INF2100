package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class CompoundStatm extends Statement{

    private StatmList st;

    CompoundStatm(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; CompundStatm
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * --> (array) --> [Statmlist] --> (end)
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return cs  object CopundStatm
     */
    public static CompoundStatm parse(Scanner s) {
        enterParser("compound statm");
        CompoundStatm cs = new CompoundStatm(s.curLineNum());

        s.skip(beginToken);
        cs.st = StatmList.parse(s);
        s.skip(endToken);

        leaveParser("compound statm");
        return cs;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax --> Statement
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */
    @Override void prettyPrint() {
        Main.log.prettyPrintLn("begin");
	Main.log.prettyIndent();
        st.prettyPrint();
	Main.log.prettyOutdent();
	Main.log.prettyPrintLn();
        Main.log.prettyPrint("end");
    }

    @Override public String identify() {
        return "<compound statm> on line " + lineNum;
    }
}
