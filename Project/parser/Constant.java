package parser;
import scanner.*;
import static scanner.TokenKind.*;

public class Constant extends PascalSyntax{

	PrefixOperator pOp = null;
	UnsignedConstant uc;

	Constant(int lNum){
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram;  Constant
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * ? == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 statement)
	 * --> ? [prefix opr] ? --> [unsigned constant]-->
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return c  object Constant
	 */
	public static Constant parse(Scanner s) {
		enterParser("constant");
		Constant c = new Constant(s.curLineNum());

		if(s.curToken.kind == addToken || s.curToken.kind == subtractToken)
			c.pOp = PrefixOperator.parse(s);
		c.uc = UnsignedConstant.parse(s);
		leaveParser("constant");
		return c;
	}

	/**
	 * Abstract code beautifiers, inherited from PascalSyntax
	 *
	 * If the object of PrefixOperator is null, we know not to ad an PrefixOperator
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	@Override void prettyPrint(){
		if(pOp != null){
			pOp.prettyPrint();
		}
		uc.prettyPrint();
	}

	@Override public String identify() {
		return "<constant> on line " + lineNum;
	}
}
