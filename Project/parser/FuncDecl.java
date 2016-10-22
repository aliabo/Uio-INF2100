package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class FuncDecl extends ProcDecl{

	private Block progBlock;
	private TypeName tName;
	private ParamDeclList pList;

	FuncDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Func decl
	 * functions always return a value
	 * This is a non-terminal representing a declaration, a subclass of PascalDecl
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
	 *
	 * Z' == 0, in combination with '*' and '?'
	 * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
	 *
	 * --> (function) --> [name] --> Z [param decl list] ? --> ( : ) --> [type name] --> ( ; ) --> [block] --> ( ; )
	 *
	 * Special condition, name; we use {@link package.test} if nametoken (else testError)
	 * we also need to update, so a call for {@link package.readNextToken}
	 * if we have a ' ( ' we also have a [terminal]
	 * s.skip(), [non-terminal] and new block
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
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

		s.skip(semicolonToken);
		leaveParser("func decl");
		return f;
	}

	/**
	 * Abstract code beautifiers, inherited from PascalSyntax --> PascalDecl --> ProDecl
	 *
	 * If paramdecl is present, we print it, either way we proceed
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
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

	@Override void check(Block curScope, Library lib) {
	
		curScope.addDecl(name, this);
		if(pList != null)
			pList.check(progBlock, lib);
		tName.check(progBlock, lib);
		progBlock.check(progBlock, lib);
	}

	@Override public String identify() {
		return "<func decl> " + name + " on line " + lineNum;
	}

	void checkWhetherAssignable(PascalSyntax where){
		where.error("function is not assignable!");
	}

        void checkWhetherFunction(PascalSyntax where){}

        void checkWhetherProcedure(PascalSyntax where){
		where.error("function is not a procedure!");
	}

        void checkWhetherValue(PascalSyntax where){
		where.error("function is not a value!");
	}
}
