package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

// <var-decl-part> ::= 'var' <var-decl>+
public class VarDeclPart extends PascalSyntax{

	ArrayList<VarDecl> vDeclList;


	VarDeclPart(int lNum) {
		super(lNum);
		vDeclList = new ArrayList<>();
	}

	@Override public String identify() {
		return "<var decl part> on line " + lineNum;
	}

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

	@Override void prettyPrint() {
		
		Main.log.prettyPrintLn("var ");
		Main.log.prettyIndent();
		for(VarDecl v:vDeclList)
			v.prettyPrint(); 
		Main.log.prettyOutdent();		
	}

}
