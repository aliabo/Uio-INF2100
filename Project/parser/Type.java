package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <type> ::= <type-name> | <array-type>
public abstract class Type extends PascalSyntax{
	
	
	Type(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<type> on line " + lineNum;
	}

	public static Type parse(Scanner s) {
         
		enterParser("type");
		Type type = null;
		switch (s.curToken.kind) {

			case arrayToken:
				type = ArrayType.parse(s); break;
			default:
				type = TypeName.parse(s); break;
		}
		leaveParser("type");
		return type;
	}

	@Override void prettyPrint() {
		
	}
}
