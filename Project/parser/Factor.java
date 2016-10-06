package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <factor> ::= <unsigned-constant> | <variable> | <func-call> | <inner-expr> | <negation>
public abstract class Factor extends PascalSyntax {
	

	Factor(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<factor> on line " + lineNum;
	}


	public static Factor parse(Scanner s) {
         
		enterParser("factor");
		Factor f = null;
		switch (s.curToken.kind) {
			case notToken:
				f = Negation.parse(s); break;
			case leftParToken:
				f = InnerExpr.parse(s); break;
			case nameToken:
				switch (s.nextToken.kind) {
					case leftParToken:
						f = FuncCall.parse(s); break;
					case leftBracketToken:
                                                f = Variable.parse(s); break;
					default:
                                                f = Variable.parse(s); //consider assign as default
						leaveParser("factor");
						return f;
						
				} 
			default:
				f = Variable.parse(s); break;
		}
		leaveParser("factor");
		return f;
	}

	@Override void prettyPrint() {
		
	}
}
