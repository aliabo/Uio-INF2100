package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ProcDecl extends PascalDecl{

    private Block progBlock;
    public ParamDeclList pList;

    ProcDecl(String id, int lNum) {
        super(id, lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; Proc Decl
     * Procedure cant return a value
     * This is a non-terminal representing a declaration, a subclass of PascalDecl
     *
     * Make a note that the parser has started parsing a non-terminal.
     *
     * Z' == 0, in combination with '*' and '?'
     * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
     *
     * -- (procedure) -- [name] -- Z [param-decl-list] ? ( ; ) [block] ( ; )
     *
     * Special condition, name; we use package.Scanner.test if nametoken (else testError)
     * we also need to update, so a call for package.Scanner.readNextToken
     * if we have a ' ( ' we also have a [terminal]
     * s.skip(), parse[non-terminal] and new [block]
     *
     * Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return p  object ProcDecl
     */
    public static ProcDecl parse(Scanner s) {
        enterParser("proc decl");
        s.skip(procedureToken);
        s.test(nameToken);
        ProcDecl p = new ProcDecl(s.curToken.id, s.curLineNum());

        s.readNextToken();

        if(s.curToken.kind == leftParToken)
            p.pList = ParamDeclList.parse(s);
        s.skip(semicolonToken);
        p.progBlock = Block.parse(s);
        p.progBlock.context = p;
        s.skip(semicolonToken);

        leaveParser("proc decl");
        return p;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax -- PascalDecl
     *
     * NameToken, if exlist not null we print and remove
     * If not null, print else proceed
     *
     * Calls the logFile package.main.log.prettyPrint, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */
    public @Override void prettyPrint() {
        Main.log.prettyPrint("procedure " + name);
        if(pList != null){
            Main.log.prettyPrint(" ");
            pList.prettyPrint();
        }
        Main.log.prettyPrintLn(";");
        progBlock.prettyPrint();
        Main.log.prettyPrintLn(";");
        Main.log.prettyPrintLn();

    }

    // Set proc decl, its curent block. add to decl list in block
    // If paramdecl list is not empty, check and check this block
    @Override void check(Block curScope, Library lib) {
	progBlock.outerScope = curScope;
	curScope.addDecl(name, this);
	if(pList != null)
		pList.check(progBlock, lib);
	
	progBlock.check(progBlock, lib);
    }

    @Override public String identify() {
	if(lineNum == 0)//defined in the library
		return "<proc decl> " + name + " in the Library";
        else
		return "<proc decl> " + name + " on line " + lineNum;
    }

    void checkWhetherAssignable(PascalSyntax where){
        where.error("procedure is not assignable!");
    }
    void checkWhetherFunction(PascalSyntax where){
        where.error(name + "is a procedure, not a function.");
    }
    void checkWhetherProcedure(PascalSyntax where){}
    void checkWhetherValue(PascalSyntax where){
        where.error("procedure is not a value!");
    }
}
