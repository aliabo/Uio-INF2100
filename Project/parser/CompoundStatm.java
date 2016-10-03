package parser;
import main.*;

class CompoundStatm extends Statment{
	CompoundStatm(int lNum) {
	super(lNum);
	}
	Statement body;

	public static Statement parse(Scanner s) {
		enterParser("CompundStatm");
	
		CompoundStatm cs = new CompoundStatm(s.curLineNum());
		s.skip(beginToken);
	
		//Når man kaller parse, skal første symbol være lest inn

		// Når man returnerer fra en parse, skal første symbol etter konstruksjonen være lest.
		while(s.curToken.kind == semicolonToken){
			s.skip(semicolonToken);
			cs.body = Statement.parse(s);
		}

		s.skip(endToken);
		leaveParser("CompundStatm");
		return cs;
	}
}
