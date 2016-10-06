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
	 * It can + or the other, if found s.skip(non-terminal)
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(non-terminal), send it to specific parser [terminal]
	 *
	 * @return n  object PrefixOperator
	 */
	public static PrefixOperator parse(Scanner s) {
		enterParser("prefix-operator");
		PrefixOperator p = new PrefixOperator(s.curLineNum());

		if(s.curToken.kind == addToken){
			p.k = "+";
			s.skip(addToken);
		}
		else{
			p.k = "-";
			s.skip(subtractToken);
		}

		leaveParser("prefix-operator");
		return p;
	}

	@Override void prettyPrint(){
		Main.log.prettyPrint(k);
	}

	@Override public String identify() {
		return "<prefix-operator> on line " + lineNum;
	}

}
