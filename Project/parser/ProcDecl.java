package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//<proc-decl> ::= 'procedure' <name> <param-decl-list>? ';' <block> ';'
public class ProcDecl extends PascalDecl{

	Block progBlock;
	ParamDeclList pList;

	ProcDecl(String id, int lNum) {
		super(id, lNum);
	}

	@Override public String identify() {
		return "<proc-decl> " + name + " on line " + lineNum;
	}

	public static ProcDecl parse(Scanner s) {
		enterParser("proc-decl");
		s.skip(procedureToken);
		s.test(nameToken);
		ProcDecl p = new ProcDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();
		if(s.curToken.kind == leftParToken)
			p.pList = ParamDeclList.parse(s);
		s.skip(semicolonToken);
		p.progBlock = Block.parse(s);
		s.skip(semicolonToken);
		leaveParser("proc-decl");
		return p;
	}
       
        public @Override void prettyPrint() {
		Main.log.prettyPrint("procedure " + name + " ");
		if(pList != null)
			pList.prettyPrint();
		Main.log.prettyPrintLn(";");
		progBlock.prettyPrint();
		Main.log.prettyPrintLn(";"); 
                
	}

}
