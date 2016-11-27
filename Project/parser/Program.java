package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Program extends PascalDecl {

	private Block progBlock;
	Program(String id, int lNum) {
		super(id, lNum);
		declLevel = 1;
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; Program
	 * Pascal 2016 program simply an [block] with a name
	 * This is a non-terminal representing a declaration, a subclass of PascalDecl
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * -- [program] -- [name] -- ( ; ) [block] ( . ) --
	 *
	 * Special condition, name; we use package.Scanner.test if nametoken (else testError)
	 * we also need to update, so a call for package.Scanner.readNextToken
	 * s.skip(), [non-terminal] and new [block]
	 * If we reach an . were in end of file
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(), send it to specific parser [non - terminal]
	 *
	 * @return p  object Program
	 */
	public static Program parse(Scanner s) {
		enterParser("program");

		s.skip(programToken);
		s.test(nameToken);
		Program p = new Program(s.curToken.id, s.curLineNum());
		s.readNextToken();

		s.skip(semicolonToken);
		p.progBlock = Block.parse(s);
		p.progBlock.context = p;

		if(s.curToken.kind != dotToken)
			Main.error(-1,"Expected a . but found a e-o-f!");
		else{
			s.readNextToken();
		}
		leaveParser("program");
		return p;
	}

	public @Override void prettyPrint() {
		Main.log.prettyPrintLn("program " + name + ";");
		progBlock.prettyPrint();

		Main.log.prettyPrint(".");
	}

	/**
	 * To check the program, we write a recursive method that traverses it
	 * Program is the most outer binding to the library
	 * When a program is initialized, we bind the program to its library
	 * This is the current, blocks outer scoop.
	 *
	 * @param curScope	current block
	 * @param lib		library connected
	 */
	public @Override void check(Block curScope, Library lib){
		progBlock.level = declLevel;
		progBlock.outerScope = lib;
		progBlock.check(curScope,lib);
	}

	@Override public String identify() {
		return "<program> " + name + " on line " + lineNum;
	}

	void checkWhetherAssignable(PascalSyntax where){
		where.error("program is not assignable!");
	}

	void checkWhetherFunction(PascalSyntax where){
		where.error("program is not a function!");
	}

	void checkWhetherProcedure(PascalSyntax where){
		where.error("program is not a procedure!");
	}

	void checkWhetherValue(PascalSyntax where){
		where.error("program is not a value!");
	}

	/**
	 * The start, were generating of code starts and declevel i set.
	 * Set up for starting and terminating the program after the block gencode is called.
	 *
	 * @param f		Code file to be generated after traversing the tree
	 */
	@Override public void genCode(CodeFile f) {
		progProcFuncName = f.getLabel(name);
		progProcFuncName = "prog$" + progProcFuncName;
		progBlock.label = progProcFuncName;
		f.genInstr("", ".globl", "main", "");
		f.genInstr("main", "", "", "");
		f.genInstr("", "call", progProcFuncName, "Start program");
		f.genInstr("", "movl", "$0,%eax", "Set status 0 and");
		f.genInstr("", "ret", "", "terminate the program");
		progBlock.genCode(f);
		f.genInstr("", "leave", "", "End of " + name);
		f.genInstr("", "ret", "", "");
	}
}
