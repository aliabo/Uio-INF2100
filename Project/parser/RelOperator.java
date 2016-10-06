package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//<rel-operator> ::= '=' | '<>' | '<' | '<=' | '>' | '>='
public class RelOperator extends Operator {


	String str = null;

	RelOperator(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<rel-operator> on line " + lineNum;
	}

	public static RelOperator parse(Scanner s) {
		enterParser("rel-operator");

		RelOperator ro = new RelOperator(s.curLineNum());
		switch(s.curToken.kind){
			case equalToken:
				ro.str = "=";
				s.skip(equalToken);break;
			case notEqualToken:
				ro.str = "<>";
				s.skip(notEqualToken);break;
			case lessToken:
				ro.str = "<";
				s.skip(lessToken);break;
			case lessEqualToken:
				ro.str = "<=";
				s.skip(lessEqualToken);break;
			case greaterToken:
				ro.str = ">";
				s.skip(greaterToken);break;
			case greaterEqualToken:
				ro.str = ">=";
				s.skip(greaterEqualToken);break;
			default:
				s.skip(equalToken);
		}
        }

        leaveParser("rel-operator");
        return ro;
	}

	void prettyPrint(){
		Main.log.prettyPrint(str);
    	}
}
