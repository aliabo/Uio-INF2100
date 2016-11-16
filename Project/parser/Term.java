package parser;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;
import main.*;

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
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'One' == 1, in combination with '*' and '?'
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 *
	 * -- One [factor] * -- One [factor opr] * --
	 * It can be 1 or several factors
	 * while we have ( * ) | ( div ) | ( mod ) | ( and ) we know we gave a factor opr
	 * add factor opr to list, add facor
	 *
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
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

	/**
	 * Their will always be 1 facor, so we get the first from the list
	 * and check and set type.
	 * We go through the list if factors i + 1
	 * check, and set name. (and check to get same as ifis compiler)
	 * check type, and set type
	 *
	 * @param curScope 	current scoop
	 * @param lib		library (bind)
	 */
	@Override void check(Block curScope, Library lib){
		Factor f = factorList.get(0);
		f.check(curScope, lib);
		type = f.type;
		for(int i = 0; i < factorOprList.size(); i++){
			Factor f2 = factorList.get(i+1);
			f2.check(curScope, lib);
			String oprName = factorOprList.get(i).str;
			if(oprName.equals("and")) // to get the same logfile as ifis compiler
				oprName = "'and'";
			type.checkType(f2.type,"left " + oprName + " operand", this,
					"Operands to " + oprName + " are of different type!");
			f2.type.checkType(type,"right " + oprName + " operand", this,
					"Operands to " + oprName + " are of different type!");
			type = f2.type;
		}
	}

	@Override public String identify() {
		return "<term> on line " + lineNum;
	}

	@Override void genCode(CodeFile f) {
		Factor ff = factorList.get(0);
		ff.genCode(f);

	}
}
