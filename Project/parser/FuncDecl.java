package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class FuncDecl extends ProcDecl{

	private Block progBlock;
	private TypeName tName;
	public ParamDeclList pList;
	FuncDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Func decl
	 * functions always return a value
	 * This is a non-terminal representing a declaration, a subclass of PascalDecl
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * Z' == 0, in combination with '*' and '?'
	 * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
	 *
	 * -- (function) -- [name] -- Z [param decl list] ? -- ( : ) -- [type name] -- ( ; ) -- [block] -- ( ; )
	 *
	 * Special condition, name; we use package.Scanner.test if nametoken (else testError)
	 * we also need to update, so a call for package.Scanner.readNextToken
	 * if we have a ' ( ' we also have a [terminal]
	 * s.skip(), [non-terminal] and new block
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non- terminal]
	 *
	 * @return f  object FuncDecl
	 */
	public static FuncDecl parse(Scanner s) {
		enterParser("func decl");
		s.skip(functionToken);
		s.test(nameToken);
		FuncDecl f = new FuncDecl(s.curToken.id, s.curLineNum());
		s.readNextToken();

		if(s.curToken.kind == leftParToken)
			f.pList = ParamDeclList.parse(s);
		s.skip(colonToken);
		f.tName = TypeName.parse(s);
		s.skip(semicolonToken);
		f.progBlock = Block.parse(s);
		f.progBlock.context = f;

		s.skip(semicolonToken);
		leaveParser("func decl");
		return f;
	}

	/**
	 * Abstract code beautifiers, inherited from PascalSyntax -- PascalDecl -- ProDecl
	 *
	 * If paramdecl is present, we print it, either way we proceed
	 *
	 * Calls the logFile package.main.log.prettyPrint, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 */
	public @Override void prettyPrint() {
		Main.log.prettyPrint("function " + name + " ");
		if(pList != null)
			pList.prettyPrint();
		Main.log.prettyPrint(": ");
		tName.prettyPrint();
		Main.log.prettyPrintLn(";");
		progBlock.prettyPrint();
		Main.log.prettyPrintLn(";");
		Main.log.prettyPrintLn();
	}

	/**
	 * We set the current block, and add name, and current decl
	 * We also set typename.
	 * Recursively call the outer block
	 *
	 * @param curScope 	current scope
	 * @param lib		connected library
	 */
	@Override void check(Block curScope, Library lib) {
		progBlock.outerScope = curScope;
		progBlock.level = declLevel;
		declLevel--;//variabel can be used in the context
		curScope.addDecl(name, this);
		progBlock.addDecl(name,this);
		declOffset = 8;


		if(pList != null){
			for(ParamDecl p : pList.pList)//each parameter
			  p.declOffset += 4;
			pList.check(progBlock, lib);
		}
		tName.check(progBlock, lib);
		type = tName.type;
		progBlock.check(progBlock, lib);
	}

	@Override public String identify() {
		return "<func decl> " + name + " on line " + lineNum;
	}

	/**
	 *  In any declarations that may be left in an assignment void checkWhetherAssignable(PascalSyntax where) is lef blank
	 *
	 * @param where indicating where in your program type occurs
	 */
	void checkWhetherAssignable(PascalSyntax where){}

	void checkWhetherFunction(PascalSyntax where){}

	void checkWhetherProcedure(PascalSyntax where){
		where.error(name + " is a function, not a procedure.");
	}

	void checkWhetherValue(PascalSyntax where){
		where.error("function is not a value!");
	}

	@Override void genCode(CodeFile f) {

		progProcFuncName = f.getLabel(name);
		progProcFuncName = "func$" + progProcFuncName;
		progBlock.genCode(f);
		f.genInstr("", "movl", "-32(%ebp),%eax", "Fetch return value");
		f.genInstr("", "leave", "", "End of " + name);
		f.genInstr("", "ret", "", "");

	}
}
