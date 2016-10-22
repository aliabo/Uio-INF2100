package parser;
public class TypeDecl extends PascalDecl{

	TypeDecl(String id, int lNum) {
		super(id, lNum);
	}
	void checkWhetherAssignable(PascalSyntax where){
		where.error("type is not assignable!");
	}

        void checkWhetherFunction(PascalSyntax where){
		where.error("type is not function!");
	}

        void checkWhetherProcedure(PascalSyntax where){
		where.error("type is not a procedure!");
	}

        void checkWhetherValue(PascalSyntax where){
		where.error("type is not a value!");
	}
	
	public @Override void prettyPrint() {
	}

	@Override public String identify() {return "";}

	@Override void check(Block curScope, Library lib) {}
}
