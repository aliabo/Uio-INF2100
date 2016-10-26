package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Library extends Block{

	types.ArrayType arrayType;
	types.BoolType booleanType;
	types.CharType charType;
	types.IntType integerType;
        ConstDecl trueType;
        ConstDecl falseType;
        ConstDecl eolType;
        ProcDecl writeType;
	public Library(int lNum){

		super(lNum);
	}

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
		addDecl("eol",tDecl);
		
		booleanType = new types.BoolType();
		tDecl = new TypeDecl("boolean",0);
		tDecl.setType(booleanType);
		addDecl("boolean",tDecl);

		integerType = new types.IntType();
		tDecl = new TypeDecl("integer",0);
		tDecl.setType(integerType);
		addDecl("integer",tDecl);
		
		charType = new types.CharType();
		tDecl = new TypeDecl("char",0);
		tDecl.setType(charType);
		addDecl("char",tDecl);
/*
		arrayType = new types.ArrayType();
		tDecl = new TypeDecl("array",0);
		tDecl.setType(arrayType);
		addDecl("array",new TypeDecl("array",0));*/
	}

}
