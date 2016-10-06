package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;


class AssignStatm extends Statement {

    private Variable var = null;
    private Expression ex = null;

    AssignStatm(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; assign statm
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * --> [variable] --> ( := ) --> [expression]
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(non-terminal), send it to specific parser [terminal]
     *
     * @return a  object ArrayType
     */
    public static AssignStatm parse(Scanner s) {
        enterParser("assignstatm");
        AssignStatm as = new AssignStatm(s.curLineNum());

        as.var = new Variable.parse(s);
        s.skip(assignToken);
        as.ex = new Expression.parse(s);

        return as;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax --> Statement --> Assignstatm
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */
    @Override void prettyPrint() {
        var.prettyprint();
        Main.log.prettyPrint(" :=");
        ex.prettyPrint();
    }

    @Override
    public String identify() {
        return "<assignstatm> on line " + lineNum;
    }
}
