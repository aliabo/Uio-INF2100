package parser;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

// <simple-expr> ::= <prefix-operator>? <term> (<term-operator> <term>)*
public class SimpleExpr extends PascalSyntax{

	private PrefixOperator pOpr;
	private ArrayList<Term> termList;
	private ArrayList<TermOperator> termOprList;
	public types.Type type = null;

	SimpleExpr(int lNum) {
		super(lNum);
		termList = new ArrayList<>();
		termOprList = new ArrayList<>();
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; SimpleExpr
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'One' == 1, in combination with '*' and '?'
	 * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 *
	 * --> ? [simple-operator] --> One [term] --> [term-operator] * -->
	 *
	 * Check if we have a term.opr +, -
	 * parse PrefixOp
	 * test : +, - , or
	 * 1 or several TermOpr
	 *
	 * s.skip(), [non - terminal]
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return se  object SimpleExpr
	 */
	public static SimpleExpr parse(Scanner s) {
		enterParser("simple expr");
		SimpleExpr se = new SimpleExpr(s.curLineNum());

		if(s.curToken.kind == addToken || s.curToken.kind == subtractToken)
			se.pOpr = PrefixOperator.parse(s);
		se.termList.add(Term.parse(s));
		while(s.curToken.kind == addToken || s.curToken.kind == subtractToken
				|| s.curToken.kind == orToken){

			se.termOprList.add(TermOperator.parse(s));
			se.termList.add(Term.parse(s));
		}

		leaveParser("simple expr");
		return se;
	}

	/**
	 * Abstract code beautifiers, inherited from PascalSyntax --> PascalDecl
	 *
	 * If prefixOpr, is present, print 0 - 1
	 * while their is termOprs in list, print em and remove
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	@Override void prettyPrint() {
		if(pOpr != null)
			pOpr.prettyPrint();
               
		termList.get(0).prettyPrint();
		for(int i = 0; i < termOprList.size(); i++){
			termOprList.get(i).prettyPrint();
			termList.get(i+1).prettyPrint();
		}
	}

	@Override void check(Block curScope, Library lib){
		
		Term t = termList.get(0);
                t.check(curScope, lib);
        	type = t.type;
		//checking that term is integer if there is a prefix operator
		if(pOpr != null)
			type.checkType(lib.integerType, pOpr.k + " operands", this,
                    	"Operands to " + pOpr.k + " are of different type!");
            		type = lib.integerType;
        	for(int i = 0; i < termOprList.size(); i++){
            		Term t2 = termList.get(i+1);
			t2.check(curScope, lib);
            		String oprName = termOprList.get(i).str;
            		type.checkType(t2.type,"left " + oprName + " operand", this,
                    	"Operands to " + oprName + " are of different type!");
			
			t2.type.checkType(type,"right " + oprName + " operand", this,
                    	"Operands to " + oprName + " are of different type!");
			type = t2.type;
        	}
        }

	@Override public String identify() {
		return "<simple expr> on line " + lineNum;
	}
}
