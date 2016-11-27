package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

public class FuncCall extends Factor {

	private String name;
	private ArrayList<Expression> expList;
	private FuncDecl funcRef = null;

	FuncCall(int lNum) {
		super(lNum);
		expList = new ArrayList<>();
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Func call
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'One' == 1, in combination with '*' and '?'
	 *  Z' == 0, in combination with '*' and '?'
	 * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
	 * '|' == OR (indicates that after this '|' symbol, it can be this OR the other terminal)
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 *
	 * -- [name] -- Z ( '(' ) -- ( '(' ) One [expression] ( , ) [expression] * ( ')' ) ?
	 *
	 * Special condition, name; we use package.Scanner.test if nametoken (else testError)
	 * we also need to update, so a call for package.Scanner.readNextToken}
	 * If we find a ' ( ' we s.skip() and add to list.Expression(can be several)
	 * as long as we find a ' , '
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
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
	 * Abstract code beautifiers, inherited from PascalSyntax -- Factor
	 *
	 * If list of Expression is bigger then 0, we prettyprint, remove from list until empty
	 *
	 * Calls the logFile package.main.log.prettyPrint, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	@Override void prettyPrint() {
		Main.log.prettyPrint(name);
		if(expList.size() > 0){

			Main.log.prettyPrint("(");
			expList.get(0).prettyPrint();
			for(int i = 1; i < expList.size(); i++){
				Main.log.prettyPrint(", ");
				expList.get(i).prettyPrint();
			}
			Main.log.prettyPrint(")");
		}
	}

	/**
	 * Here the Name occurrences,  it will be be an assignment
	 * we need to check: checkWhetherFunction(this);
	 * Out recursive traverses the tree, and check type.
	 * We cast back to funcdecl and set type
	 * We check number of parameters
	 * We want to know that parameters has same type as in declaration of function
	 * @param curScope	current scoop
	 * @param lib		library (bind)
	 */
	@Override void check(Block curScope, Library lib){
		PascalDecl d = curScope.findDecl(name,this);
		d.checkWhetherFunction(this);
		funcRef = (FuncDecl)d;
		type = funcRef.type;
		if(expList.size()!=0){
			if(expList.size() > funcRef.pList.pList.size())
				error("Too many parameters in call on " + funcRef.name + "!");
			else if (expList.size() < funcRef.pList.pList.size())
				error("Too few parameters in call on " + funcRef.name + "!");
			for(int i = 0; i < expList.size(); i++){
				Expression funcCallParam = expList.get(i);
				ParamDecl funcDeclParam = funcRef.pList.pList.get(i);
				funcCallParam.check(curScope,lib);
				funcCallParam.type.checkType(funcDeclParam.type,"param #" + (i+1), this,
						"Illegal type of parameter #"+ (i+1) + "!");
			}
		}
	}

	@Override public String identify() {
		return "<func call> on line " + lineNum;
	}

	/**
	 * We traverse the list of expressions and let the expressions handle their own code generating.
	 * If explist is not 0, we add the value * 4
	 *
	 * @param f		Code file to be generated after traversing the tree
	 */
	@Override void genCode(CodeFile f) {
		for (int i = expList.size() -1 ; i >=0 ;i--){
				expList.get(i).genCode(f);
				f.genInstr("", "pushl", "%eax", "Push param #" + (i + 1));
		}
		f.genInstr("", "call", funcRef.progProcFuncName,"");
		if(expList.size() != 0)
			f.genInstr("", "addl", "$" + (4 * expList.size()) + ",%esp", "Pop parameters");

	}
}
