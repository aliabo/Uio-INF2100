package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <array-type> ::= 'array' '[' <constant> '..' <constant> ']' 'of' <type>
public class ArrayType extends Type{

	Constant c1;
	Constant c2;
	Type type;


	ArrayType(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<array-type> on line " + lineNum;
	}

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
		a.type = Type.parse(s);
		leaveParser("array-type");
		return a;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint("array ["); 
		c1.prettyPrint();
		Main.log.prettyPrint("..");
		c2.prettyPrint();
		Main.log.prettyPrint("] of");
		type.prettyPrint();
                Main.log.prettyPrintLn(""); 
	}

}
