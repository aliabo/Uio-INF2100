package parser;

public class Library extends Block{

	types.ArrayType arrayType;
	types.BoolType booleanType;
	types.CharType charType;
	types.IntType integerType;

	public Library(int lNum){

		super(lNum);
	}

	public Library(){
		super(0);
	}

}
