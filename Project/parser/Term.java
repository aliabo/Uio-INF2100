package parser;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

public class Term extends PascalSyntax{

	private ArrayList<Factor> factorList;
	private ArrayList<FactorOperator> factorOprList;
        public types.Type type = null;

	Term(int lNum) {
		super(lNum);
		factorList = new ArrayList<>();
		factorOprList = new ArrayList<>();
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Term
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'One' == 1, in combination with '*' and '?'
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 *
	 * --> One [factor] * --> One [factor opr] * -->
	 * It can be 1 or several factors
	 * while we have ( * ) | ( div ) | ( mod ) | ( and ) we know we gave a factor opr
	 * add factor opr to list, add facor
	 *
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return t  object Term
	 */
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

	// While list is not empty, pretty print, reduce
	@Override void prettyPrint() {

		factorList.get(0).prettyPrint();
		for(int i = 0 ; i < factorOprList.size();i++){
			factorOprList.get(i).prettyPrint();
			factorList.get(i+1).prettyPrint();
		}
	}
	
	@Override void check(Block curScope, Library lib){
		
		Factor f = factorList.get(0);
                f.check(curScope, lib);
        	type = f.type;
        	for(int i = 0; i < factorOprList.size(); i++){
            		Factor f2 = factorList.get(i+1);
			f2.check(curScope, lib);
            		String oprName = factorOprList.get(i).str;
            		type.checkType(f2.type, oprName + " operands", this,
                    	"Operands to " + oprName + " are of different type!");
        	}
        }

	@Override public String identify() {
		return "<term> on line " + lineNum;
	}
}
