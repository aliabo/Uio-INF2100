package parser;
import scanner.*;

public abstract class UnsignedConstant extends Factor {
	
	protected types.Type type = null;
	public int constVal; 

	UnsignedConstant(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Unsigned Constant
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * --> [name] | [numeric literal] | [char literal] -->
	 * If any of the 3, parse() break;
	 * or {@link package.testError}
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return uc  object UnsignedConstant
	 */
	public static UnsignedConstant parse(Scanner s) {
		enterParser("unsigned constant");
		UnsignedConstant uc = null;

		switch (s.curToken.kind) {
			case nameToken:
				uc = NamedConst.parse(s); break;
			case intValToken:
				uc = NumberLiteral.parse(s); break;
			case charValToken:
				uc = CharLiteral.parse(s); break;
			default:
				s.testError("value");
		}
		leaveParser("unsigned constant");
		return uc;
	}

	@Override void check(Block curScope, Library lib){
		
	}

	@Override void prettyPrint() {}

	@Override public String identify() {
		return "<unsigned constant> on line " + lineNum;
	}
}
