package parser;
import scanner.*;

public class EmptyStatm extends Statement{

		EmptyStatm(int lNum){
 		super(lNum);
        }

	@Override public String identify() {
		return "<empty-statm> on line " + lineNum;
	}

	public static EmptyStatm parse(Scanner s) {
		enterParser("empty-statm");
                
		leaveParser("empty-statm");
		return new EmptyStatm(s.curLineNum());
	}
       
        @Override void prettyPrint() {
	
	}

}
