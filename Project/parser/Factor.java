package parser;
import scanner.*;
import main.*;

public abstract class Factor extends PascalSyntax {

    public types.Type type;
    public PascalDecl varRef = null;

    Factor(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Factor
     *
     * Make a note that the parser has started parsing a non-terminal.
     *
     * -- [unsigned-constant] | [variable] | [func-call] | [inner-expr] | [negation]
     * FACTOR can be one of this, we need to check our curToken, if found we break
     * In case of "not" [negation], In case of "("  [inner-expr]...
     * Thats how the language is defined
     *
     *
     * Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(terminal), send it to specific parser [non - terminal]
     *
     * @return f  object Factor (the pointer we get back by calling f = subclass.pars())
     */
    public static Factor parse(Scanner s) {

        enterParser("factor");
        Factor f = null;
        switch (s.curToken.kind) {
            case notToken:
                f = Negation.parse(s); break;
            case leftParToken:
                f = InnerExpr.parse(s); break;
            case nameToken:
                switch (s.nextToken.kind) {
                    case leftParToken:
                        f = FuncCall.parse(s);
			leaveParser("factor");
                        return f;
                    case leftBracketToken:
                        f = Variable.parse(s);
			leaveParser("factor");
                        return f;
                    default:
                        f = Variable.parse(s); //consider assign as default
                        leaveParser("factor");
                        return f;
                }
            case charValToken:
                f = UnsignedConstant.parse(s); break;
	    case intValToken:
		f = UnsignedConstant.parse(s); break;
	    default:
		s.testError("value"); //consider assign as default
        }
        leaveParser("factor");
        return f;
    }

    @Override void prettyPrint() {}

    @Override void check(Block curScope, Library lib){}

    @Override public String identify() {
        return "<factor> on line " + lineNum;
    }

    @Override void genCode(CodeFile f) {}
}
