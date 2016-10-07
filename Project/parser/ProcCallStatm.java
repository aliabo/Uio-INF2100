package parser;
import main.Main;
import scanner.Scanner;
import java.util.ArrayList;
import static scanner.TokenKind.*;

// ? = 0 or 1
// --> [name] --> ? ( ( ) --> [expression] --> ( , )
class ProcCallStatm extends Statement {

    private ArrayList<Expression> exList = new ArrayList<>();
    private String name;


    ProcCallStatm(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; ProcCallStatm
     * functions always return a value
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * Z' == 0, in combination with '*' and '?'
     * 'One' == 1, in combination with '*' and '?'
     * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
     * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
     *
     * --> [name] --> Z  ( '(' ) --> One [expression] * --> ( , )  ? -->
     *
     * Special condition, name; we use {@link package.test} if nametoken (else testError)
     * we also need to update, so a call for {@link package.readNextToken}
     * if we have a ' ( ' we also have a [terminal]
     * s.skip(non-terminal), [terminal]
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(non-terminal), send it to specific parser [terminal]
     *
     * @return pc  object ProcCallStatm
     */
    public static ProcCallStatm parse(Scanner s) {
        enterParser("proc call");
        s.test(nameToken);
        ProcCallStatm pc = new ProcCallStatm(s.curLineNum());
	pc.name = s.curToken.id;
        s.readNextToken();
	
        if(s.curToken.kind == leftParToken) {
	    
            s.skip(leftParToken);
            pc.exList.add(Expression.parse(s));

            while(s.curToken.kind == commaToken){
                s.skip(commaToken);
                pc.exList.add(Expression.parse(s));
            }
            s.skip(rightParToken);
        }
        //pc.name = TypeName.parse(s);
        leaveParser("proc call");
        return pc;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax --> Statment
     *
     * NameToken, if exlist not null we print and remove
     * List is empty, new line
     * Print all in list
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */
    @Override void prettyPrint() {
        Main.log.prettyPrint(name);
        if (exList != null) {
            Main.log.prettyPrint("(");
            exList.get(0).prettyPrint();
            exList.remove(0);

            for(Expression ex: exList){
                Main.log.prettyPrint(",");
                ex.prettyPrint();
            }
            Main.log.prettyPrint(")");
        }
    }

    @Override public String identify() {
        return "<proc call> on line " + lineNum;
    }
}
