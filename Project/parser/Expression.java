package parser;
import scanner.*;
import static scanner.TokenKind.*;
import main.*;

public class Expression extends PascalSyntax {

    Expression(int lNum) {
        super(lNum);
    }

    private SimpleExpr sE1 = null;
    private SimpleExpr sE2 = null;
    private RelOperator rO = null;
    public types.Type type = null;

    /**
     * Parser method to declare the language, explained as a rail-diagram; Expression
     *
     * Make a note that the parser has started parsing a non-terminal.
     *
     * 'Z' == 0, in combination with '*' and '?'
     * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
     * -- [Simple expres] -- Z [rel opr] -- [simple expres] ?
     *
     * If curToken is any of the bool types we know its a real op. it can be 0 or 1
     * and if real op, 1 simple expr
     *
     * Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return ex  object Expression
     */
    public static Expression parse(Scanner s) {
        enterParser("expression");
        Expression ex = new Expression(s.curLineNum());
        ex.sE1 = SimpleExpr.parse(s);

        if(s.curToken.kind == equalToken || s.curToken.kind == notEqualToken ||
                s.curToken.kind == lessToken ||s.curToken.kind == lessEqualToken ||
                s.curToken.kind == greaterToken ||s.curToken.kind == greaterEqualToken) {

            ex.rO = RelOperator.parse(s);
            ex.sE2 = SimpleExpr.parse(s);
        }

        leaveParser("expression");
        return ex;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax
     *
     * If RelOperator is not null, we know, to prettyprint
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style)
     */
    void prettyPrint(){
        sE1.prettyPrint();
        if(rO != null){
            rO.prettyPrint();
            sE2.prettyPrint();
        }
    }

    /**
     * We recursively call the next, simpleExpr is checked, type set
     * if next simpleExpr not null, we check, get name, checks type
     * set boolean, we set the element type curToken is =, <>, <, etc.
     *
     * @param curScope current scope
     * @param lib       connected library
     */
    @Override void check(Block curScope, Library lib) {
        sE1.check(curScope, lib);
        type = sE1.type;
        if (sE2 != null) {
            sE2.check(curScope, lib);
            String oprName = rO.str;
            type.checkType(sE2.type, oprName + " operands", this,
                    "Operands to " + oprName + " are of different type!");
            type = lib.booleanType;
        }
    }

    @Override public String identify() {
        return "<expression> on line " + lineNum;
    }

    @Override void genCode(CodeFile f) {
	sE1.genCode(f);
        if (sE2 != null) {
            sE2.genCode(f);

            if(type instanceof types.BoolType){
                f.genInstr("", "movl", "-4(%ebp),%edx", "");
                rO.genCode(f);
            }
	    //TODO


        }

    }
}
