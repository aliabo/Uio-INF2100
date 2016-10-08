package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
/* <program> ::= ’program’ <name> ’;’ <block> ’.’ */

//TODO implement abstract from ParscalDecl
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
                p.progBlock.context = p;//TODO check if needed p.progBlock.context = p;????
		s.skip(dotToken);
		leaveParser("program");
		return p;
	}
        //TODO check if needed or not
       
        public @Override void prettyPrint() {
		Main.log.prettyPrintLn("program " + name + ";"); 
		progBlock.prettyPrint();
		
                Main.log.prettyPrint("."); 
	}
}
