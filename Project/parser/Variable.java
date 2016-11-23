package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Variable extends Factor {

	public Expression exp;
	private String name;

	Variable(int lNum) {
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Variable
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
	 * -- [name] -- ? ( [ ) -- [expression] -- ( ] )  --
	 * [name], we have a special condition, need to test
	 * create object
	 * readNextToken()
	 * We have left ( [ )
	 * s.skip() parse() s.skip()
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return v  object Variable
	 */
	public static Variable parse(Scanner s) {
		enterParser("variable");
		Variable v = new Variable(s.curLineNum());

		//s.test(nameToken);
		v.name = s.curToken.id;
		s.readNextToken();

		if(s.curToken.kind == leftBracketToken){
			s.skip(leftBracketToken);
			v.exp = Expression.parse(s);
			s.skip(rightBracketToken);
		}

		leaveParser("variable");
		return v;
	}

	@Override void prettyPrint() {
		Main.log.prettyPrint(name);
		if(exp != null){
			Main.log.prettyPrint("[");
			exp.prettyPrint();
			Main.log.prettyPrint("]");
		}
	}

	/**
	 * Find decl, if instance if type.Arraytype, cast, and set element type
	 * else set type, If expression, we check recursively.
	 * cast and checking index. type
	 *
	 * @param curScope	current scoop
	 * @param lib		library (bind)
	 */
	@Override void check(Block curScope, Library lib) {
		varRef = curScope.findDecl(name,this);

		if(varRef.type instanceof types.ArrayType){//types.ArrayType
			types.ArrayType a = (types.ArrayType)varRef.type;
			type = a.elemType;
		}
		else{
			type = varRef.type;
		}
		if(exp != null) {
			exp.check(curScope,lib);
			// testing index type
			types.ArrayType a = (types.ArrayType)varRef.type;
			a.indexType.checkType(exp.type,"array index",this, "array indexes are of different types");
		}
		System.out.println(name +identify() + value);

	}

	@Override public String identify() {
		return "<variable> on line " + lineNum;
	}

	@Override void genCode(CodeFile f) {
		if (exp != null){//array not implemented 
			exp.genCode(f);
			VarDecl v = (VarDecl)varRef;
			ArrayType t = (ArrayType)v.t;
			if(t.c1.constVal != 0) //array[c1..c2]
				f.genInstr("","subl", "$" + t.c1.constVal+ ",%eax", "");
			f.genInstr("","movl", ""+ (-4 * varRef.declLevel)+"(%ebp),%edx", "");
			f.genInstr("","leal", ""+ (varRef.declOffset + (exp.value * 4)) +"(%edx),%edx", "");
			//f.genInstr("","popl", "%ecx", "");
			f.genInstr("","movl", "(%edx,%eax,4),%eax", "");
		}
		else if(varRef instanceof ConstDecl){//constant
			ConstDecl c = (ConstDecl)varRef;
			f.genInstr("","movl", "$" + c.con.constVal+ ",%eax", "  " + c.con.constVal);
		}else{//variable
			if(varRef instanceof TypeDecl){
					TypeDecl t = (TypeDecl)varRef;
					if(t.decl instanceof ConstDecl){
						ConstDecl c = (ConstDecl)t.decl;
						if(c.name.equals("eol")){//eol
							f.genInstr("","movl", "$10,%eax", "  10");
						}
						else if(c.name.equals("true")){//true
							f.genInstr("","movl", "$1,%eax", "  1");
						}
						else if(c.name.equals("false")){//false
							f.genInstr("","movl", "$0,%eax", "  0");
						}
					}
			}else{
				f.genInstr("","movl", "" + (-4)*varRef.declLevel+ "(%ebp),%edx", "");
				f.genInstr("","movl", varRef.declOffset+ "(%edx)" + ",%eax", "  " + varRef.name);
			}
		}
	}

}
