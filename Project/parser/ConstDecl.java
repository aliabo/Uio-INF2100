package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

//TODO implement abstract from ParscalDecl
//<const-decl> ::= <name> '=' <constant> ';'
public class ConstDecl extends PascalDecl{

	Constant con;

	ConstDecl(String id, int lNum) {
		super(id, lNum);
	}

	@Override public String identify() {
		return "<const-decl> " + name + " on line " + lineNum;
	}

	public static ConstDecl parse(Scanner s) {
		enterParser("const-decl");
		s.test(nameToken);
		ConstDecl cDecl = new ConstDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();
		s.skip(equalToken);
		cDecl.con = Constant.parse(s);
		s.skip(semicolonToken);
		leaveParser("const-decl");
		return cDecl;
	}
       
        public @Override void prettyPrint() {
		Main.log.prettyPrint(name + " = "); 
		con.prettyPrint();
                Main.log.prettyPrintLn(";"); 
	}
}
