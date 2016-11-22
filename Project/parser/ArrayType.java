package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ArrayType extends Type{

	public Constant c1;
	private Constant c2;
	public Type t;


	ArrayType(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; array-type
	 *<p>
	 * Make a note that the parser has started parsing a non-terminal.
	 *<p>
	 * -- (array) -- ( [ ) -- [constant] -- ( .. ) -- [constant] -- ( ] ) -- (of) -- [type]
	 * <p>
	 * Makes an pointer to the specified object in this(ArrayType), now connected to an object
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return a  object ArrayType
	 */
	public static ArrayType parse(Scanner s) {

		enterParser("array-type");
		ArrayType a = new ArrayType(s.curLineNum());
		s.skip(arrayToken);
		s.skip(leftBracketToken);
		a.c1 = Constant.parse(s);
		s.skip(rangeToken);
		a.c2 = Constant.parse(s);
		s.skip(rightBracketToken);
		s.skip(ofToken);
		a.t = Type.parse(s);
		leaveParser("array-type");
		return a;
	}

	/**
	 * Abstract code beautifiers, inherited from PascalSyntax --> Type
	 *
	 * -- (array) -- ( [ ) -- [constant] -- ( .. ) -- [constant] -- ( ] ) -- (of) -- [type]
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	@Override void prettyPrint() {
		Main.log.prettyPrint("array [");
		c1.prettyPrint();
		Main.log.prettyPrint("..");
		c2.prettyPrint();
		Main.log.prettyPrint("] of ");
		t.prettyPrint();
	}

	/**
	 * The recursive method check, we nee to check in params;
	 * 1. c2.type - is kind of like "our" type should be compared with
	 * 2. "array  limits" - This is an indication of how the type used
	 * 3. this - here specifies where in your program type occurs
	 * 4. message contains the message to be given if it is the type of error
	 * same type as any other variable declarations inserted in decl HashMap
	 *
	 * @param curScope 	current scoop
	 * @param lib		library (bind)
	 */
	@Override void check(Block curScope, Library lib){
		c1.check(curScope, lib);
		c2.check(curScope, lib);
		t.check(curScope, lib);
		//index type test
		c1.type.checkType(c2.type,"array limits",this,"" +c1.type.identify()+ " "+ c2.type.identify() +" are different types!");
		type = new types.ArrayType(t.type, c1.type, c1.uConst.constVal, c2.uConst.constVal);
	}

	@Override public String identify() {
		return "<array-type> on line " + lineNum;
	}

	@Override void genCode(CodeFile f) {}
}
