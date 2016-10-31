package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class RelOperator extends Operator {


    protected String str = null;
    protected types.Type type = null;

    RelOperator(int lNum) {
        super(lNum);
    }


    /**
     * Parser method to declare the language, explained as a rail-diagram; Rel operator
     *
     * Make a note that the parser has started parsing a non-terminal.
     *
     * Rel operator can be one of this, we need to check our curToken, if found we break
     * and s.skip(non-terminal)
     * Save name in str, for print
     *
     * Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return ro  object RelOperator
     */
    public static RelOperator parse(Scanner s) {
        enterParser("rel opr");

        RelOperator ro = new RelOperator(s.curLineNum());
        switch(s.curToken.kind){
            case equalToken:
                ro.str = "=";
                s.skip(equalToken);break;
            case notEqualToken:
                ro.str = "<>";
                s.skip(notEqualToken);break;
            case lessToken:
                ro.str = "<";
                s.skip(lessToken);break;
            case lessEqualToken:
                ro.str = "<=";
                s.skip(lessEqualToken);break;
            case greaterToken:
                ro.str = ">";
                s.skip(greaterToken);break;
            case greaterEqualToken:
                ro.str = ">=";
                s.skip(greaterEqualToken);break;
            default:
                s.skip(equalToken);
        }

        leaveParser("rel opr");
        return ro;
    }

    void prettyPrint(){
        Main.log.prettyPrint(" "+ str + " ");
    }

    @Override
    void check(Block curScope, Library lib) {

    }

    @Override public String identify() {
        return "<rel operator> on line " + lineNum;
    }
}
