package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class TermOperator extends Operator {

    public String str;

    TermOperator(int lNum){
        super(lNum);
    }

    @Override public String identify() {
        return "<term opr> on line " + lineNum;
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Term Operator
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * --> ( + ) | ( - ) | ( or ) -->
     * If any of this tokens we save to object name, s.skip()
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return t  object TermOperator
     */
    public static TermOperator parse(Scanner s) {
        enterParser("term opr");
        TermOperator to = new TermOperator(s.curLineNum());

        if(s.curToken.kind == addToken){
            to.str = "+";
            s.skip(addToken);

        }else if (s.curToken.kind == subtractToken){
            to.str = "-";
            s.skip(subtractToken);
        }
        else{
            to.str = "or";
            s.skip(orToken);
        }
        leaveParser("term opr");
        return to;
    }

    @Override void check(Block curScope, Library lib){}

    @Override void prettyPrint(){
        Main.log.prettyPrint(" " + str + " ");
    }
}
