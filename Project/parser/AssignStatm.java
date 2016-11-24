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
     * Make a note that the parser has started parsing a non-terminal.
     *
     * -- [variable] -- ( := ) -- [expression]
     *
     * Make a note that the parser has finished parsing a non-terminal.
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
     * -- [variable] -- ( := ) -- [expression]
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

    /**
     * Here the Name occurrences,  it will be be an assignment
     * we need to check: checkWhetherAssignable(this);
     * Out recursive traverses the tree, and check type.
     *
     * @param curScope  current scope
     * @param lib       library bind
     */
    @Override void check(Block curScope, Library lib) {
        var.check(curScope, lib);
        type = var.type;
        var.varRef.checkWhetherAssignable(this);

        ex.check(curScope, lib);
        type.checkType(ex.type, ":=", this, "Different types in assignment!");
    }

    @Override public String identify() {
        return "<assign statm> on line " + lineNum;
    }

    @Override void genCode(CodeFile f) {
        ex.genCode(f);

        if( var.exp != null){ //array[exp] :=
            f.genInstr("","pushl", "%eax", "");
            var.exp.genCode(f);
            VarDecl v = (VarDecl)var.varRef;
            types.ArrayType t = (types.ArrayType)v.type;
            if(t.loLim != 0) //array[c1..c2]
              f.genInstr("","subl", "$" + t.loLim+ ",%eax", "");
            f.genInstr("","movl", ""+ (-4 * var.varRef.declLevel)+"(%ebp),%edx", "");
            f.genInstr("","leal", "" + var.varRef.declOffset  + "(%edx),%edx", "");
            f.genInstr("","popl", "%ecx", "");
            f.genInstr("","movl", "%ecx,0(%edx,%eax,4)", var.varRef.name + "[x] :=");
        }
        else if (var.varRef instanceof FuncDecl){//func :=
          f.genInstr("","movl", "" + (-4)*(var.varRef.declLevel + 1)+ "(%ebp),%edx", "");
          f.genInstr("","movl", "%eax,-32(%edx)", var.varRef.name  + " :=");

        }else{ //var :=
          f.genInstr("","movl", "" + (-4)*var.varRef.declLevel+ "(%ebp),%edx", "");
          f.genInstr("","movl", "%eax," + var.varRef.declOffset+ "(%edx)", var.varRef.name + " :=");
        }
    }
}
