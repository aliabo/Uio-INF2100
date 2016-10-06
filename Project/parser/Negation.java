package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <negation> ::= 'not' <factor>
public class Negation extends Factor {

	Factor f;

	Negation(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<negation> on line " + lineNum;
	}

	public static Negation parse(Scanner s) {
         
		enterParser("negation");
		Negation n = new Negation(s.curLineNum());
			
		s.skip(notToken);
		n.f = Factor.parse(s);
			
		leaveParser("negation");
		return n;
	}

	@Override void prettyPrint() {
		
		Main.log.prettyPrint("not ");
		f.prettyPrint();
		Main.log.prettyPrintLn("");
	}
}
