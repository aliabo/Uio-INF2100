package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class IfStatm extends Statement {

    private Statement stat1 = null;
    private Statement stat2 = null;
    private Expression exp = null;

    IfStatm(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; If - Statm
     *
     * Make a note that the parser has started parsing a non-terminal.
     *
     * 'Z' == 0, in combination with '*' and '?'
     * ? == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 statement)
     * -- (if) -- [expression] -- (then) -- [statment] -- Z (else) -- [statment] ? --
     *
     * Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return ifS  object IfStatm
     */
    public static IfStatm parse(Scanner s) {
        enterParser("if-statm");

        IfStatm ifS = new IfStatm(s.curLineNum());
        s.skip(ifToken);
        ifS.exp = Expression.parse(s);

        s.skip(thenToken);
        ifS.stat1 = Statement.parse(s);

        if(s.curToken.kind == elseToken) {
            s.skip(elseToken);
            ifS.stat2 = Statement.parse(s);
        }

        leaveParser("if-statm");
        return ifS;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax -- Statement
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */
    @Override void prettyPrint(){
        Main.log.prettyPrint("if ");
        exp.prettyPrint();
        Main.log.prettyPrintLn(" then");
        Main.log.prettyIndent();
        stat1.prettyPrint();
        Main.log.prettyOutdent();

        if(stat2 != null){
            Main.log.prettyPrintLn();
            Main.log.prettyPrintLn("else");
            Main.log.prettyIndent();
            stat2.prettyPrint();
            Main.log.prettyOutdent();
        }
    }

    @Override void check(Block curScope, Library lib) {
        exp.check(curScope, lib);
        exp.type.checkType(lib.booleanType, "if-test", this,
                "If-test is not Boolean.");
        stat1.check(curScope, lib);
        if(stat2 != null)
            stat2.check(curScope, lib);
    }

    @Override public String identify() {
        return "<if-statm> on line " + lineNum;
    }


    @Override void genCode(CodeFile f) {
      //f.genInstr("","movl", "" + (-4)*varRef.declLevel+ "(%ebp),%edx", "");
      String label = f.getLocalLabel(),
      elseLabel = "";
      f.genInstr("", "", "", "Start if-statement");
      exp.genCode(f);
      f.genInstr("", "cmpl", "$0,%eax", "");
      f.genInstr("", "je", label, "");
      stat1.genCode(f);
      if(stat2 != null){
        elseLabel = f.getLocalLabel();
        f.genInstr("", "jmp", elseLabel, "");
      }

      f.genInstr(label, "", "", "");
      if(stat2 != null){
        stat2.genCode(f);
      }
      f.genInstr(elseLabel, "", "", "End if-statement");
    }
}
