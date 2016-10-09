package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class PrefixOperator extends Operator {

	private String k;

	PrefixOperator(int lNum){
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Prefix Operator
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * '|' == OR (indicates that after this '|' symbol, it can be this OR the other terminal)
	 *
	 * --> ( + ) | ( - ) -->
	 * It can + or the other, if found s.skip()
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return n  object PrefixOperator
	 */
	public static PrefixOperator parse(Scanner s) {
		enterParser("prefix opr");
		PrefixOperator p = new PrefixOperator(s.curLineNum());

		if(s.curToken.kind == addToken){
			p.k = "+";
			s.skip(addToken);
		}
		else{
			p.k = "-";
			s.skip(subtractToken);
		}

		leaveParser("prefix opr");
		return p;
	}

	@Override void prettyPrint(){
		Main.log.prettyPrint(" " +k + " ");
	}

	@Override public String identify() {
		return "<prefix opr> on line " + lineNum;
	}

}
