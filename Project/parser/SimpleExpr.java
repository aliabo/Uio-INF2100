package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

// <simple-expr> ::= <prefix-operator>? <term> (<term-operator> <term>)*
public class SimpleExpr extends PascalSyntax{

	PrefixOperator pOpr;
	ArrayList<Term> termList;
	ArrayList<TermOperator> termOprList;


	SimpleExpr(int lNum) {
		super(lNum);
		termList = new ArrayList<>();
		termOprList = new ArrayList<>();
	}

	@Override public String identify() {
		return "<simple-expr> on line " + lineNum;
	}

	public static SimpleExpr parse(Scanner s) {
         
		enterParser("simple-expr");
		SimpleExpr se = new SimpleExpr(s.curLineNum());
		if(s.curToken.kind == addToken || s.curToken.kind == subtractToken)
			se.pOpr = PrefixOperator.parse(s);
		termList.add(Term.parse(s));
		while(s.curToken.kind == addToken || s.curToken.kind == subtractToken
		        || s.curToken.kind == orToken){
			
			se.termOprList.add(TermOperator.parse(s));
			se.termList.add(Term.parse(s));
		}
		
		leaveParser("simple-expr");
		return se;
	}

	@Override void prettyPrint() {
		if(pOpr != null)
			pOpr.prettyPrint();
		termList.get(0).prettyPrint();
		termList.remove(0);
		while(termOprList.size()>0){
			termOprList.get(0).prettyPrint();
			termList.get(0).prettyPrint();
			termOprList.remove(0);
			termList.remove(0);
		}
	}
}
