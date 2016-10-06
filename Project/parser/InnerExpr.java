package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class InnerExpr extends Factor {

	private Expression exp;

	InnerExpr(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; InnerExpr
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * --> ( '(' ) [expression] ( ')' )
	 * s.skip(non-terminal) parse terminal s.skip(non-terminal)
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(non-terminal), send it to specific parser [terminal]
	 *
	 * @return iExp  object InnerExpr
	 */
	public static InnerExpr parse(Scanner s) {
		enterParser("inner-expr");
		InnerExpr iExp = new InnerExpr(s.curLineNum());

		s.skip(leftParToken);
		iExp.exp = Expression.parse(s);
		s.skip(rightParToken);

		leaveParser("inner-expr");
		return iExp;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint("(");
		exp.prettyPrint();
		Main.log.prettyPrint(")");
	}

	@Override public String identify() {
		return "<inner-expr> on line " + lineNum;
	}
}
