package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

public class VarDeclPart extends PascalSyntax{

	private ArrayList<VarDecl> vDeclList;

	VarDeclPart(int lNum) {
		super(lNum);
		vDeclList = new ArrayList<>();
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Var decl Part
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'One' == 1, in combination with '*' and '?'
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 *
	 * -- ( var ) --  One [var decl] * --
	 * It can be 1 or many var decl, so .skip() and while nameToken do; add to list
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return v  object VArDeclPart
	 */
	public static VarDeclPart parse(Scanner s) {
		enterParser("var decl part");
		VarDeclPart v = new VarDeclPart(s.curLineNum());
		s.skip(varToken);

		do{
			v.vDeclList.add(VarDecl.parse(s));
		}while(s.curToken.kind == nameToken);

		leaveParser("var decl part");
		return v;
	}

	// Recursively go through every declared vardecl in list
	@Override void check(Block curScope, Library lib) {
		for(VarDecl vDecl: vDeclList){
			vDecl.check(curScope, lib);
		}	
	}

	@Override void prettyPrint() {
		
		Main.log.prettyPrintLn("var ");
		Main.log.prettyIndent();
		for(VarDecl v:vDeclList)
			v.prettyPrint(); 
		Main.log.prettyOutdent();		
	}

	@Override public String identify() {
		return "<var decl part> on line " + lineNum;
	}

	@Override void genCode(CodeFile f) {}
}
