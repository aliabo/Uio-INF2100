package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

abstract class Statement extends PascalSyntax {

	
	Statement(int lNum) {
		super(lNum);
	}

	public static Statement parse(Scanner s) {
         
		enterParser("statement");
		Statement st = null;
		switch (s.curToken.kind) {
			case beginToken:
				st = CompoundStatm.parse(s); break;
			case ifToken:
				st = IfStatm.parse(s); break;
			case nameToken:
				//TODO check!!!
				switch (s.nextToken.kind) {
					case assignToken:
						st = AssignStatm.parse(s); break;
					case leftBracketToken:
						st = AssignStatm.parse(s); break;
 
					default:
						st = ProcCallStatm.parse(s);
						leaveParser("statement");
						return st;						
				} break;
			case whileToken:
				st = WhileStatm.parse(s); break;
			default:
				st = EmptyStatm.parse(s); break;
		}
		leaveParser("statement");
		return st;
	}

	@Override public String identify() {
		return "<statement> on line " + lineNum;
	}

	@Override void prettyPrint() {
		
	}


}
