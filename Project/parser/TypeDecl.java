package parser;
public class TypeDecl extends PascalDecl{
        
        PascalDecl decl = null;
	types.Type type = null;

	TypeDecl(String id, int lNum) {
		super(id, lNum);
	}



	void setDecl(PascalDecl decl){

		this.decl = decl;
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

	public void setType(types.Type type){
		this.type = type;
	}
	@Override public String identify() {
		if (decl != null){
        		return name + " " + decl.name + " in the library";
		}
		else{
			return name +  " in the library";	
		}
        }

	@Override void check(Block curScope, Library lib) {}
}
