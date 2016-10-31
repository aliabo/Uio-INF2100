/**
 * This is a class that is used to provide all the elements of the library the same type as Pascaldecl
 *
 */
package parser;

public class TypeDecl extends PascalDecl{

	PascalDecl decl = null;

	TypeDecl(String id, int lNum) {
		super(id, lNum);
	}

	void setDecl(PascalDecl decl){
		this.decl = decl;
	}

	public void setType(types.Type type){
		this.type = type;
	}

	void checkWhetherAssignable(PascalSyntax where){
		where.error(name + "type is not assignable!");
	}

	void checkWhetherFunction(PascalSyntax where){
		where.error(name + " is not function!");
	}

	void checkWhetherProcedure(PascalSyntax where){
		if(decl != null)
			decl.checkWhetherProcedure(where);
		else
			where.error(decl.name + " is not procedure!");
	}

	void checkWhetherValue(PascalSyntax where){
		if(decl != null){
			if(decl instanceof ProcDecl){
				where.error(decl.name + " is not a value!");
			}
		}
		else{
			if (type instanceof types.BoolType)
				where.error("boolean is not a value!");
		}

	}

	@Override public String identify() {
		if (decl != null){
			return name + " " + decl.name + " in the library";
		}
		else{
			return name +  " in the library";
		}
	}

	@Override void prettyPrint() {}

	@Override void check(Block curScope, Library lib) {}
}
