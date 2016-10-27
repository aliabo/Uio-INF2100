package parser;
import scanner.*;

public abstract class Type extends PascalSyntax{

	public types.Type type;

	Type(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Type
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * --> [type-name] | [array-type] -->
	 * If any of, parse()
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return type  object Type
	 */
	public static Type parse(Scanner s) {

		enterParser("type");
		Type t = null;
		switch (s.curToken.kind) {

			case arrayToken:
				t = ArrayType.parse(s); break;
			default:
				t = TypeName.parse(s); break;
		}
		leaveParser("type");
		return t;
	}

	@Override void prettyPrint() {}

	@Override void check(Block curScope, Library lib){
	}

	@Override public String identify() {
		return "<type> on line " + lineNum;
	}
}
