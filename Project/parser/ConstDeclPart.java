package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

public class ConstDeclPart extends PascalSyntax{

	private ArrayList<ConstDecl> cDeclList = new ArrayList<>();

	ConstDeclPart(int lNum) {
		super(lNum);
	}


	/**
	 * Parser method to declare the language, explained as a rail-diagram; Const Decl Part
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'One' == 1, in combination with '*' and '?'
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 * --> (const) -->  One [const decl] * -->
	 *
	 * Special condition, it can be 1 or several of [const decl], arrayList used to add
	 * while we have nameToken
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return cdPart  object ConstDeclPart
	 */
	public static ConstDeclPart parse(Scanner s) {
		enterParser("const decl part");
		ConstDeclPart cdPart = new ConstDeclPart(s.curLineNum());

		s.skip(constToken);
		do{
			cdPart.cDeclList.add(ConstDecl.parse(s));
			cdPart.cDeclList.get(cdPart.cDeclList.size()-1);
		}while(s.curToken.kind == nameToken);

		leaveParser("const decl part");
		return cdPart;
	}


	/**
	 * Abstract code beautifiers, inherited from PascalSyntax
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	@Override void prettyPrint() {
		Main.log.prettyPrintLn("const ");
		Main.log.prettyIndent();
		for(ConstDecl c: cDeclList)
			c.prettyPrint();
		Main.log.prettyOutdent();
	}

	@Override public String identify() {
		return "<const decl part> on line " + lineNum;
	}
	
	public @Override void check(Block curScope, Library lib){
		for(ConstDecl cDecl: cDeclList)
			cDecl.check(curScope, lib);		
	}

}	
