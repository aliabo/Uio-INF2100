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
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * Where one in Java type ' \ ''  to get a single quotes, we write in Pascal2016 ''''.
	 * -- ( ' ) -- [char except'] -- ( ' )
	 * 			-- ( ' ) -- ( ' ) --
	 *
	 * [Non-terminal] [char except'] are already taken care of by the scanner (for now)
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
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

	/**
	 * All elements of the syntax tree containing a constant
	 * we declare an "int constVal" and this can be calculated
	 * by check which could go through the whole tree.
	 * Set type as chartype
	 *
	 * @param curScope current scope
	 * @param lib		library (bind)
	 */
	@Override void check(Block curScope, Library lib){
		type = lib.charType;
        }

	@Override void prettyPrint() {
		Main.log.prettyPrint("'" + c + "'");		
	}

	@Override public String identify() {
		return "<char literal> on line " + lineNum;
	}
	
	@Override void genCode(CodeFile f) {
		int ascii = String.valueOf(c).codePointAt(0);
		f.genInstr("", "movl", "$"+ascii+",%eax", "  '"+c+"'");	
		f.genInstr("", "pushl", "%eax", "Push next param.");
	}
}
