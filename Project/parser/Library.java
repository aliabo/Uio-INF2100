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
		writeType =new ProcDecl("write",0);
		addDecl("true",trueType);
		addDecl("false",falseType);
		addDecl("eol",eolType);
		addDecl("write",writeType);
		addDecl("integer",new TypeDecl("integer",0));
		addDecl("boolean",new TypeDecl("boolean",0));
		addDecl("array",new TypeDecl("array",0));
		addDecl("char",new TypeDecl("char",0));
	}

}
