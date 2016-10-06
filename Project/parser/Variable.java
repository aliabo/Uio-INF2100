package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <variable> ::= <name> ('[' <expression> ']')?
public class Variable extends Factor {

	Expression exp;
	String name;

	Variable(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<variable> on line " + lineNum;
	}

	public static Variable parse(Scanner s) {
         
		enterParser("variable");
		Variable v = new Variable(s.curLineNum());
		s.test(nameToken);
		v.name = s.curToken;
		s.readNextToken();
		if(s.curToken.kind == leftBracketToken){
			s.skip(leftBracketToken);
			v.exp = Expression.parse(s);
			s.skip(rightBracketToken);
		}

		leaveParser("variable");
		return v;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint(name);
		if(exp != null){
			Main.log.prettyPrint("[ ");
			exp.prettyPrint();
			Main.log.prettyPrintLn(" ]");
		} 
	}
}
