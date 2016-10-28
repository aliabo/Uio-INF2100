package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;
import java.util.HashMap;

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

	// Needed to set all in the TypeDecl class to override indetify as declared in library
	public Library(){
		
		super(0);
		writeType = new ProcDecl("write",0);
		TypeDecl tDecl = new TypeDecl("<proc decl>",0);
		tDecl.setDecl(writeType);
		addDecl("write",tDecl);

		trueType = new ConstDecl("true",0);
		tDecl = new TypeDecl("<const decl>",0);
		tDecl.setDecl(trueType);
		addDecl("true",tDecl);

		falseType = new ConstDecl("false",0);
		tDecl = new TypeDecl("<const decl>",0);
		tDecl.setDecl(falseType);
		
		addDecl("false",tDecl);
		
		eolType = new ConstDecl("eol",0);
		tDecl = new TypeDecl("<const decl>",0);
		tDecl.setDecl(eolType);
		tDecl.setType(charType);
		addDecl("eol",tDecl);
		
		booleanType = new types.BoolType();
		tDecl = new TypeDecl("<type decl> boolean",0);
		tDecl.setType(booleanType);
		addDecl("boolean",tDecl);

		integerType = new types.IntType();
		tDecl = new TypeDecl("<type decl> integer",0);
		tDecl.setType(integerType);
		addDecl("integer",tDecl);
		
		charType = new types.CharType();
		tDecl = new TypeDecl("<type decl> char",0);
		tDecl.setType(charType);
		addDecl("char",tDecl);
/*
		arrayType = new types.ArrayType();
		tDecl = new TypeDecl("array",0);
		tDecl.setType(arrayType);
		addDecl("array",new TypeDecl("array",0));*/
	}

}
