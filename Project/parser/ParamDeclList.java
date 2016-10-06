package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

public class ParamDeclList extends PascalSyntax{

    private ArrayList<ParamDecl> pList;

    ParamDeclList(int lNum) {
        super(lNum);
        pList = new ArrayList<>();
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Param Decl List
     * functions always return a value
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * 'One' == 1, in combination with '*' and '?'
     * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
     *
     * --> ( '(' ) [param-decl] ( ; ) One [param-decl] * ( ')' )
     * while curToken not like ')' then s,skip(non-terminal) parse.[terminal] to array list
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(non-terminal), send it to specific parser [terminal]
     *
     * @return p  object ParamDecList
     */
    public static ParamDeclList parse(Scanner s) {
        enterParser("param-decl-list");
        s.skip(leftParToken);
        ParamDeclList p = new ParamDeclList(s.curLineNum());
        p.pList.add(ParamDecl.parse(s));

        while(s.curToken.kind != rightParToken){
            s.skip(semicolonToken);
            p.pList.add(ParamDecl.parse(s));
        }

        s.skip(rightParToken);
        leaveParser("param-decl-list");
        return p;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax
     *
     * For all out paramdecl in list prettyprint em
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */
    public @Override void prettyPrint() {
        Main.log.prettyPrint("( ");
        pList.get(0).prettyPrint();
        pList.remove(0);
        for(ParamDecl p: pList){
            Main.log.prettyPrint("; ");
            p.prettyPrint();
        }
        Main.log.prettyPrintLn(" )");
    }

    @Override public String identify() {
        return "<param-decl-list> on line " + lineNum;
    }
}
