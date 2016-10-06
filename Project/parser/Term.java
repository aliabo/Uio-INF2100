package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <term> ::= <factor> (<factor-operator> <factor>)*
public class Term extends PascalSyntax{

	ArrayList<Factor> factrorList;
	ArrayList<FactorOperator> factorOprList;

	Term(int lNum) {
		super(lNum);
		factorList = new ArrayList<>();
		factorOprList = new ArrayList<>();
	}

	@Override public String identify() {
		return "<term> on line " + lineNum;
	}

	public static Term parse(Scanner s) {
         
		enterParser("term");
		Term t = new Term(s.curLineNum());
		t.factorList.add(Factor.parse(s));
		
		while(s.curToken.kind == multiplyToken || s.curToken.kind == divToken
		        || s.curToken.kind == modToken || s.curToken.kind == andToken){
			
			t.factorOprList.add(FactorOperator.parse(s));
			t.factorList.add(Factor.parse(s));
		}
		
		leaveParser("term");
		return t;
	}

	@Override void prettyPrint() {

		factorList.get(0).prettyPrint();
		factorList.remove(0);
		while(factorOprList.size()>0){
			factorOprList.get(0).prettyPrint();
			factorList.get(0).prettyPrint();
			factorOprList.remove(0);
			factorList.remove(0);
		}
	}
}
