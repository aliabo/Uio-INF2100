package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class CharLiteral extends UnsignedConstant {

	private char c;
	int constVal;

	CharLiteral(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Char Literal
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * Where one in Java type <' \ '' > to get a single quotes, we write in Pascal2016 <''''>.
	 * --> ( ' ) --> [char except'] --> ( ' )
	 * 			--> ( ' ) --> ( ' ) -->
	 *
	 * [Non-terminal] [char except'] are already taken care of by the scanner (for now)
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return cl  object CharLiteral
	 */
	public static CharLiteral parse(Scanner s) {
		enterParser("char literal");
		CharLiteral cl = new CharLiteral(s.curLineNum());

		s.test(charValToken);
		
		cl.c = s.curToken.charVal;
		s.readNextToken();

		leaveParser("char literal");
		return cl;
	}

	@Override void check(Block curScope, Library lib){

		type = lib.charType;
        }

	@Override void prettyPrint() {
		Main.log.prettyPrint("'" + c + "'");		
	}

	@Override public String identify() {
		return "<char literal> on line " + lineNum;
	}
}
