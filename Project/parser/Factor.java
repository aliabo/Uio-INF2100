package parser;
import scanner.*;

public abstract class Factor extends PascalSyntax {


    Factor(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Factor
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * --> [unsigned-constant] | [variable] | [func-call] | [inner-expr] | [negation]
     * FACTOR can be one of this, we need to check our curToken, if found we break
     * In case of "not" [negation], In case of "("  [inner-expr]...
     * Thats how the language is defined
     *
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(non-terminal), send it to specific parser [terminal]
     *
     * @return f  object Factor
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
                        f = FuncCall.parse(s); break;
                    case leftBracketToken:
                        f = Variable.parse(s); break;
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
		f = Variable.parse(s); //consider assign as default
        }
        leaveParser("factor");
        return f;
    }

    @Override void prettyPrint() {}

    @Override public String identify() {
        return "<factor> on line " + lineNum;
    }
}
