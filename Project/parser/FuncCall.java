package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;

// <func-call> ::= <name> ('(' <expression> ( ',' <expression> )*  ')')?
public class FuncCall extends Factor {

	String name;
	ArrayList<Expression> expList;


	FuncCall(int lNum) {
		super(lNum);
		expList = new ArrayList<>();
	}

	@Override public String identify() {
		return "<func-call> on line " + lineNum;
	}

	public static FuncCall parse(Scanner s) {
         
		enterParser("func-call");
		FuncCall fc = new FuncCall(s.curLineNum());
		s.test(nameToken);
		fc.name = s.curToken.id;
		s.readNextToken();
		
		if(s.curToken.kind == leftParToken){
			
			s.skip(leftParToken);
			fc.expList.add(Expression.parse(s));

			while(s.curToken.kind == commaToken){
				s.skip(commaToken);
				fc.expList.add(Expression.parse(s));
			}
			s.skip(rightParToken);
			
		}
		leaveParser("func-call");
		return fc;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint(name);
		if(expList.size() > 0){

			Main.log.prettyPrint("(");
			expList.get(0).prettyPrint();
			expList.remove(0);
			for(Expression exp: expList){
				Main.log.prettyPrint(",");
				exp.prettyPrint();
			}
			Main.log.prettyPrintLn(")");
		}
	}
}
