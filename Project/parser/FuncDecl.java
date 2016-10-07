package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class FuncDecl extends ProcDecl{

	private Block progBlock;
	private TypeName name;
	private ParamDeclList pList;

	FuncDecl(String id, int lNum) {
		super(id, lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Func decl
	 * functions always return a value
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
	 * s.skip(non-terminal), [terminal] and new block
	 *
	 * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(non-terminal), send it to specific parser [terminal]
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
		f.name = TypeName.parse(s);
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
		Main.log.prettyPrint(" : ");
		name.prettyPrint();
		Main.log.prettyPrintLn(";");
		progBlock.prettyPrint();
		Main.log.prettyPrintLn(";");
	}

	//abstract void checkWhetherAssignable(PascalSyntax where);
	//abstract void checkWhetherFunction(PascalSyntax where);
	//abstract void checkWhetherProcedure(PascalSyntax where);
	//abstract void checkWhetherValue(PascalSyntax where);

	@Override public String identify() {
		return "<func decl> " + name + " on line " + lineNum;
	}
}
