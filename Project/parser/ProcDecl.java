package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ProcDecl extends PascalDecl{

    private Block progBlock;
    private ParamDeclList pList;

    ProcDecl(String id, int lNum) {
        super(id, lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Proc Decl
     * Procedure cant return a value
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * Z' == 0, in combination with '*' and '?'
     * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
     *
     * --> (procedure) --> [name] --> Z [param-decl-list] ? ( ; ) [block] ( ; )
     *
     * Special condition, name; we use {@link package.test} if nametoken (else testError)
     * we also need to update, so a call for {@link package.readNextToken}
     * if we have a ' ( ' we also have a [terminal]
     * s.skip(non-terminal), [terminal] and new [block]
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(non-terminal), send it to specific parser [terminal]
     *
     * @return p  object ProcDecl
     */
    public static ProcDecl parse(Scanner s) {
        enterParser("proc decl");
        s.skip(procedureToken);
        s.test(nameToken);
        ProcDecl p = new ProcDecl(s.curToken.id, s.curLineNum());
        s.readNextToken();

        if(s.curToken.kind == leftParToken)
            p.pList = ParamDeclList.parse(s);
        s.skip(semicolonToken);
        p.progBlock = Block.parse(s);
        s.skip(semicolonToken);

        leaveParser("proc decl");
        return p;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax --> PascalDecl
     *
     * NameToken, if exlist not null we print and remove
     * If not null, print else proceed
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */
    public @Override void prettyPrint() {
        Main.log.prettyPrint("procedure " + name + " ");
        if(pList != null)
            pList.prettyPrint();
        Main.log.prettyPrintLn(";");
        progBlock.prettyPrint();
        Main.log.prettyPrintLn(";");
	Main.log.prettyPrintLn("");

    }
    //abstract void checkWhetherAssignable(PascalSyntax where);
    //abstract void checkWhetherFunction(PascalSyntax where);
    //abstract void checkWhetherProcedure(PascalSyntax where);
    //abstract void checkWhetherValue(PascalSyntax where);

    @Override public String identify() {
        return "<proc decl> " + name + " on line " + lineNum;
    }
}
