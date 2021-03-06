package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class FactorOperator extends Operator {

    public String str;

    FactorOperator(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Factor operator
     *
     * Make a note that the parser has started parsing a non-terminal.
     *
     * -- ( * ) | ( div ) | ( mod ) | ( and ) --
     * FACTOR operator can be one of this, we need to check our curToken, if found we break
     * and s.skip()
     * assign factor operator to object
     *
     * Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(non-terminal), send it to specific parser [terminal]
     *
     * @return f  object Factor operator
     */
    public static FactorOperator parse(Scanner s) {
        enterParser("factor opr");
        FactorOperator f = new FactorOperator(s.curLineNum());

        switch(s.curToken.kind){
            case multiplyToken:
                f.str = "*";
                s.skip(multiplyToken);break;
            case divToken:
                f.str = "div";
                s.skip(divToken);break;
            case modToken:
                f.str = "mod";
                s.skip(modToken);break;
            case andToken:
                f.str = "and";
                s.skip(andToken);break;
            default:
                s.skip(multiplyToken);
        }

        leaveParser("factor opr");
        return f;
    }

    @Override void prettyPrint() {
        Main.log.prettyPrint(" " + str + " ");
    }

    @Override void check(Block curScope, Library lib){}


    @Override public String identify() {
        return "<factor opr> on line " + lineNum;
    }

    @Override void genCode(CodeFile f) {}
}
