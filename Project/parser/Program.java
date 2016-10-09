package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

/* <program> ::= ’program’ <name> ’;’ <block> ’.’ */
public class Program extends PascalDecl {

	Block progBlock;

	Program(String id, int lNum) {
		super(id, lNum);
	}

	@Override public String identify() {
		return "<program> " + name + " on line " + lineNum;
	}

	public static Program parse(Scanner s) {
		enterParser("program");
		s.skip(programToken);
		s.test(nameToken);
		Program p = new Program(s.curToken.id, s.curLineNum());
		s.readNextToken();
		s.skip(semicolonToken);
		p.progBlock = Block.parse(s);
                p.progBlock.context = p;
		if(s.curToken.kind != dotToken)
			Main.error(-1,"Expected a . but found a e-o-f!");
		else{
			s.readNextToken();
		}
		leaveParser("program");
		return p;
	}
       
        public @Override void prettyPrint() {
		Main.log.prettyPrintLn("program " + name + ";"); 
		progBlock.prettyPrint();
		
                Main.log.prettyPrint("."); 
	}

	void checkWhetherAssignable(PascalSyntax where){
		where.error("program is not assignable!");
	}

        void checkWhetherFunction(PascalSyntax where){
		where.error("program is not a function!");
	}

        void checkWhetherProcedure(PascalSyntax where){
		where.error("program is not a procedure!");
	}

        void checkWhetherValue(PascalSyntax where){
		where.error("program is not a value!");
	}
}
