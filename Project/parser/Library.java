/**
 * A class to handle, and take care of predefined names
 * Like tex. names like integer, boolean that are wrote predefined.
 * Here is where we we deal with them
 */
package parser;

public class Library extends Block{

	public types.ArrayType arrayType;
	public types.BoolType booleanType;
	public types.CharType charType;
	public types.IntType integerType;
	public ConstDecl trueType;
	public ConstDecl falseType;
	public ConstDecl eolType;
	public ProcDecl writeType;


	public Library(int lNum){

		super(lNum);
	}

	/**
	 * Predefined names are bonded to the library
	 * Her we set all in the TypeDecl class to override indemnify as declared in library
	 * The declarations and we put them outside.
	 * And they will be found unless the user has declared something of the same name
	 */
	public Library(){

		super(0);
		writeType = new ProcDecl("write",0);
		TypeDecl tDecl = new TypeDecl("<proc decl>",0);
		tDecl.setDecl(writeType);
		addDecl("write",tDecl);

		booleanType = new types.BoolType();
		tDecl = new TypeDecl("<type decl> boolean",0);
		tDecl.setType(booleanType);
		addDecl("boolean",tDecl);

		trueType = new ConstDecl("true",0);
		tDecl = new TypeDecl("<const decl>",0);
		tDecl.setType(booleanType);
		tDecl.setDecl(trueType);
		addDecl("true",tDecl);

		falseType = new ConstDecl("false",0);
		tDecl = new TypeDecl("<const decl>",0);
		tDecl.setType(booleanType);
		tDecl.setDecl(falseType);
		addDecl("false",tDecl);

		charType = new types.CharType();
		tDecl = new TypeDecl("<type decl> char",0);
		tDecl.setType(charType);
		addDecl("char",tDecl);

		eolType = new ConstDecl("eol",0);
		tDecl = new TypeDecl("<const decl>",0);
		tDecl.setDecl(eolType);
		tDecl.setType(charType);
		addDecl("eol",tDecl);

		integerType = new types.IntType();
		tDecl = new TypeDecl("<type decl> integer",0);
		tDecl.setType(integerType);
		addDecl("integer",tDecl);

		arrayType = new types.ArrayType(integerType,integerType,0,0);
		tDecl = new TypeDecl("array",0);
		tDecl.setType(arrayType);
		addDecl("array",new TypeDecl("arrayType",0));
	}

}
