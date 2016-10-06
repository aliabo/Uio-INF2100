package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

// using ? for 0 or 1
/*<block> ::= <const-decl-part>? <var-decl-part>? 
(<func-decl> | <proc-del>)* 'begin' <statm-list> 'end' */

public class Block extends PascalSyntax {

        ConstDeclPart cDeclPart = null;
        VarDeclPart vDeclPart = null;
        ArrayList<ProcDecl> pfDecl = new ArrayList<>();
        StatmList sList;
        //PascalSyntac context = null; //TODO Check if needed


	Block(int lNum){
 		super(lNum);
        }

	@Override public String identify() {
		return "<block> on line " + lineNum;
	}

	public static Block parse(Scanner s) {
		enterParser("block");
                Block b = new Block(s.curLineNum());
                //TODO check if ERORR Switch without break to continue to next possible solution
                switch (s.curToken.kind) {
			case constToken:
				b.cDelPart = ConstDeclPart.parse(s);
			case varToken:
				b.vDeclPart = VarDeclPart.parse(s);
 
                        // funcDecl og procDecl in a while
                        while(s.curToken != beginToken){
                                switch(s.curToken.kind){

                                	case functionToken:
						b.pfDecl.add(FuncDecl.parse(s)); break;
				        case procedureToken:
                                                b.pfDecl.add(ProcDecl.parse(s)); break;
					default://check if it is begin or it is wrong
						s.test(beginToken); break;
				} 
                        }
		
			default://This should be
				s.test(beginToken); break;
		}
                s.skip(beginToken);
		b.sList = StatmList.parse(s);
                s.skip(endToken);
		leaveParser("block");
		return b;
	}
       
        @Override void prettyPrint() {
		if(cDeclPart != null) cDeclPart.prettyprint();
                if(vDeclPart != null) vDeclPart.prettyprint();
                for(ProcDecl p: pfDecl){
			p.prettyprint();
                }
                Main.log.prettyPrintLn("begin"); 
                Main.log.prettyIndent();
                sList.prettyPrint();
                Main.log.prettyOutdent();
		Main.log.prettyPrintLn("end"); 
	}

}
