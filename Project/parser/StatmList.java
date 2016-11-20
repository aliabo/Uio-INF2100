package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

public class StatmList extends PascalSyntax{

    private ArrayList<Statement> stList = new ArrayList<>();


    StatmList(int lNum){
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Statm list
     *
     * Make a note that the parser has started parsing a non-terminal.
     *
     * 'One' == 1, in combination with '*' and '?'
     * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
     *
     * -- [statement] -- One ( ; ) -- [statement] * --
     * It can be 1 or several statements
     * Save in list if after first read their is a semicolonToken s.skip() and add
     *
     * Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return sl  object StatmList
     */
    public static StatmList parse(Scanner s) {
        enterParser("statm list");
        StatmList sl = new StatmList(s.curLineNum());

        sl.stList.add(Statement.parse(s));
        while(s.curToken.kind == semicolonToken){

            s.skip(semicolonToken);
            sl.stList.add(Statement.parse(s));
        }

        leaveParser("statm list");
        return sl;
    }

    @Override void prettyPrint(){

        stList.get(0).prettyPrint();


        for(int i = 1; i < stList.size();i++){
            Main.log.prettyPrintLn(";");
            stList.get(i).prettyPrint();
        }
    }

    // Go through list of statements and recursively check
    @Override void check(Block curScope, Library lib){
	for(Statement stmt : stList)
		stmt.check(curScope, lib);
    }

    @Override public String identify() {
        return "<statm list> on line " + lineNum;
    }

    @Override void genCode(CodeFile f) {
	     for(Statement stmt : stList)
		     stmt.genCode(f);
    }

}
