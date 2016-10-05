package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

/* <const-decl-part> ::= 'const' <const-decl>+ */
public class ConstDeclPart extends PascalSyntax{

	ArrayList<ConstDecl> cDeclList = new ArrayList<>();


    	ConstDeclPart(int lNum) {
		super(lNum);
	}

	@Override public String identify() {
		return "<const-decl-part> on line " + lineNum;
	}

	public static ConstDeclPart parse(Scanner s) {
         
		enterParser("const-decl-part");
		ConstDeclPart cdPart = new ConstDeclPart(s.curLineNum());

		s.skip(constToken);
		do{
                	cdPart.cDeclList.add( ConstDecl.parse(s));
		}while(s.curToken.kind == nameToken);
		
		leaveParser("const-decl-part");
		return cdPart;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint("const ");
		for(ConstDecl c: cDeclList)
			c.prettyPrint();
	}
}
