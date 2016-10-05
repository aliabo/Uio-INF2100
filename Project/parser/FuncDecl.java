package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

// <func-decl> ::= 'function' <name> <param-decl-list>? ':' <type-name> ';' <block> ';'
public class FuncDecl extends ProcDecl{


	Block progBlock;
	TypeName name;
	ParamDeclList pList;

	FuncDecl(String id, int lNum) {
		super(id, lNum);
	}

	@Override public String identify() {
		return "<func-decl> " + name + " on line " + lineNum;
	}

	public static FuncDecl parse(Scanner s) {
		enterParser("func-decl");
		s.skip(functionToken);
		s.test(nameToken);
		FuncDecl f = new FuncDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();
		if(s.curToken.kind == leftParToken)
			f.pList = ParamDeclList.parse(s);
		s.skip(colonToken);
		f.name = TypeName.parse(s);
		s.skip(semicolonToken);
		f.progBlock = Block.parse(s);
		s.skip(semicolonToken);
		leaveParser("func-decl");
		return f;
	}
       
        public @Override void prettyPrint() {
		Main.log.prettyPrint("function " + name + " ");
		if(pList != null)
			pList.prettyPrint();
		Main.log.prettyPrint(" : ");
		name.prettyPrint();
		Main.log.prettyPrintLn(";");
		progBlock.prettyPrint();
		Main.log.prettyPrintLn(";"); 
                
	}
}
