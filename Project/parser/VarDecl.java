package parser;

// <var-decl> ::= <name> ':' <type> ';'
public class VarDecl extends PascalDecl{

	Type type;

	VarDecl(String id, int lNum) {
		super(id, lNum);
	}

	@Override public String identify() {
		return "<var-decl> " + name + " on line " + lineNum;
	}

	public static VarDecl parse(Scanner s) {
		enterParser("var-decl");
		s.test(nameToken);
		VarDecl vDecl = new VarDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();
		s.skip(colonToken);
		vDecl.type = Type.parse(s);
		s.skip(semicolonToken);
		leaveParser("var-decl");
		return cDecl;
	}
       
        public @Override void prettyPrint() {
		Main.log.prettyPrint(name + " : "); 
		type.prettyPrint();
                Main.log.prettyPrintLn(" ;"); 
	}
}
