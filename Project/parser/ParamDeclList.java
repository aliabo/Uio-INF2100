package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

//<param-decl-list> ::= '(' <param-decl> (';'<param-decl>)* ')'
public class ParamDeclList extends PascalSyntax{

	ArrayList<ParamDecl> pList;

	ParamDeclList(int lNum) {
		super(lNum);
		pList = new ArrayList<>();
	}

	@Override public String identify() {
		return "<param-decl-list> on line " + lineNum;
	}

	public static ParamDeclList parse(Scanner s) {
		enterParser("param-decl-list");
		s.skip(leftParToken);
		ParamDeclList p = new ParamDeclList(s.curLineNum());
		p.pList.add(ParamDecl.parse(s));
		while(s.curToken.kind != rightParToken){
			s.skip(semicolonToken);
			p.pList.add(ParamDecl.parse(s));
		}
		
		s.skip(rightParToken);
		leaveParser("param-decl-list");
		return p;
	}
       
        public @Override void prettyPrint() {
		Main.log.prettyPrint("( ");
		pList.get(0).prettyPrint();
		pList.remove(0);
		for(ParamDecl p: pList){
			Main.log.prettyPrint("; ");			
			p.prettyPrint();
		}
		Main.log.prettyPrintLn(" )");
	}
}
