package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

public class FuncCall extends Factor {

	private String name;
	private ArrayList<Expression> expList;

	FuncCall(int lNum) {
		super(lNum);
		expList = new ArrayList<>();
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Func call
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'One' == 1, in combination with '*' and '?'
	 *  Z' == 0, in combination with '*' and '?'
	 * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
	 * '|' == OR (indicates that after this '|' symbol, it can be this OR the other terminal)
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 *
	 * --> [name] --> Z ( '(' ) --> ( '(' ) One [expression] ( , ) [expression] * ( ')' ) ?
	 *
	 * Special condition, name; we use {@link package.test} if nametoken (else testError)
	 * we also need to update, so a call for {@link package.readNextToken}
	 * If we find a ' ( ' we s.skip() and add to list.Expression(can be several)
	 * as long as we find a ' , '
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(non-terminal), send it to specific parser [terminal]
	 *
	 * @return fc  object Func call
	 */

	public static FuncCall parse(Scanner s) {
		enterParser("func call");
		FuncCall fc = new FuncCall(s.curLineNum());
		s.test(nameToken);
		fc.name = s.curToken.id;
		s.readNextToken();

		if(s.curToken.kind == leftParToken){
			s.skip(leftParToken);
			fc.expList.add(Expression.parse(s));

			while(s.curToken.kind == commaToken){
				s.skip(commaToken);
				fc.expList.add(Expression.parse(s));
			}
			s.skip(rightParToken);

		}
		leaveParser("func call");
		return fc;
	}

	/**
	 * Abstract code beautifiers, inherited from PascalSyntax --> Factor
	 *
	 * If list of Expression is bigger then 0, we prettyprint, remove from list until empty
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	@Override void prettyPrint() {
		Main.log.prettyPrint(name);
		if(expList.size() > 0){

			Main.log.prettyPrint("(");
			expList.get(0).prettyPrint();
			expList.remove(0);
			for(Expression exp: expList){
				Main.log.prettyPrint(", ");
				exp.prettyPrint();
			}
			Main.log.prettyPrint(")");
		}
	}

	@Override void check(Block curScope, Library lib){}

	@Override public String identify() {
		return "<func call> on line " + lineNum;
	}
}
