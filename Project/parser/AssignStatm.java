package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class AssignStatm extends Statement {

    private Variable var = null;
    private Expression ex = null;
    private types.Type type = null;
    
    AssignStatm(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Assign statm
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * --> [variable] --> ( := ) --> [expression]
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return as  object AssignStatm
     */
    public static AssignStatm parse(Scanner s) {
        enterParser("assign statm");
        AssignStatm as = new AssignStatm(s.curLineNum());

        as.var = Variable.parse(s);
        s.skip(assignToken);
        as.ex = Expression.parse(s);

	    leaveParser("assign statm");
        return as;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax --> Statement
     *
     * --> [variable] --> ( := ) --> [expression]
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */    
    @Override void prettyPrint() {
        var.prettyPrint();
        Main.log.prettyPrint(" := ");
        ex.prettyPrint();
    }

    @Override void check(Block curScope, Library lib) {
        var.check(curScope, lib);
	type = var.type;
        var.varRef.checkWhetherAssignable(this);
        ex.check(curScope, lib);
	type.checkType(ex.type, ":=", this,
                       var.identify()+ " "+ ex.identify() +" are of different type!");	
    }

    @Override public String identify() {
        return "<assign statm> on line " + lineNum;
    }


}
