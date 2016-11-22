package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Block extends PascalSyntax {

	private ConstDeclPart cDeclPart = null;
	public VarDeclPart vDeclPart = null;
	private ArrayList<ProcDecl> pfDecl = new ArrayList<>();
	private StatmList sList;
	public PascalSyntax context = null;
	public int level;
	public	HashMap<String,PascalDecl> decls = new HashMap<>();
	public Block outerScope = null;
	public String label = "";

	Block(int lNum){
		super(lNum);
	}

	/**
	 * Parser method to declare the language, explained as a rail-diagram; block
	 * A Pascal2016-program is simply a [block] with a name
	 * In Pascal in 2016 can have [block]s on top of each other in so many levels you want,
	 * and you may have functions that are local inside other functions
	 *
	 * Make a note that the parser has started parsing a non-terminal.
	 *
	 * 'Z' == 0, in combination with '*' and '?'
	 * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
	 * '|' == OR (indicates that after this '|' symbol, it can be this OR the other terminal)
	 * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
	 *
	 * -- Z [const-decl-part] ? -- Z [var-decl-part] ? --  Z [func-decl] * |  Z [proc-del] * -- (begin) -- [statm-list] -- (end) --
	 *
	 * We need to check if curToken.kind [const-decl-part] or [var-decl-part]
	 * While curToken is not a beginToken, we check for funcDecl og procDecl in a while, if found break (Their could be a several)
	 * if while is finished, s.kip() -- parse [non-terminal] -- skip()
	 *
	 * Make a note that the parser has finished parsing a non-terminal.
	 *
	 * @param s     is the Scanner object, of the token that the is the scanners current Token read,
	 *              s.skip(non-terminal), send it to specific parser [terminal]
	 *
	 * @return b    object Block
	 */
	public static Block parse(Scanner s) {
		enterParser("block");
		Block b = new Block(s.curLineNum());
		switch (s.curToken.kind) {
			case constToken:
				b.cDeclPart = ConstDeclPart.parse(s);
			case varToken:
				b.vDeclPart = VarDeclPart.parse(s);
			default:
				// funcDecl og procDecl in a while
				while(s.curToken.kind != beginToken){
					switch(s.curToken.kind){

						case functionToken:
							b.pfDecl.add(FuncDecl.parse(s));
							break;
						case procedureToken:
							b.pfDecl.add(ProcDecl.parse(s));
							break;
						default://check if it is begin or it is wrong
							s.test(beginToken); break;
					}
				}
				break;
		}
		s.skip(beginToken);
		b.sList = StatmList.parse(s);
		s.skip(endToken);
		leaveParser("block");
		return b;
	}

	/**
	 * Block contain a list of its declarations, in the form of a HashMap<decls>
	 * It is easy to check if any names are declared several times in the same block.
	 * And this will then give an error message
	 *
	 * @param id	Key (name)
	 * @param d		deceleration (to get bind)
	 */
	void addDecl(String id, PascalDecl d) {
		if (decls.containsKey(id))
			d.error(id + " declared twice in same block!");
		decls.put(id, d);
	}

	/**
	 * If the declaration is not local, we can find it by searching in outer scopes.
	 * Therefore Block contain a pointer Block outerScope pointing to block outside.
	 * It can be initiated by check. {@link Program}
	 *
	 * @param curScope	current scope
	 * @param lib		library (bind to scope)
	 */
	@Override void check(Block curScope, Library lib) {
		if (cDeclPart != null) {
			cDeclPart.check(this, lib);
		}

		if (vDeclPart != null) {
			vDeclPart.check(this, lib);
		}

		for(ProcDecl decl: pfDecl){
			decl.declLevel = level+1;
			decl.check(this, lib);
		}
		sList.check(this, lib);
	}

	/**
	 * A method to find the right decl.
	 * Throws an error message of not found.
	 *
	 * @param id	Key (name)
	 * @param where	Object
	 * @return		log {@link main.LogFile} for printing
	 * 				and return Decl object, else if not found
	 * 				throw an error.
	 */
	PascalDecl findDecl(String id, PascalSyntax where) {
		PascalDecl d = decls.get(id);
		if (d != null) {
			Main.log.noteBinding(id, where, d);
			return d;
		}
		if (outerScope != null)
			return outerScope.findDecl(id,where);
		where.error("Name " + id + " is unknown!");
		return null; // Required by the Java compiler.
	}


	/**
	 * Abstract code beautifiers, inherited from PascalSyntax --> Block
	 *
	 * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
	 * that adjust positioning and spacing (indent style), to make the content easier for other
	 * programmers to view, read, and understand.
	 *
	 * If not [const-decl-part] or [var-decl-part], no print
	 * go through list of procDecl, print
	 * main.log non-terminal
	 */
	@Override void prettyPrint() {
		if(cDeclPart != null)
			cDeclPart.prettyPrint();
		if(vDeclPart != null){
			vDeclPart.prettyPrint();
		}
		for(ProcDecl p: pfDecl){
			Main.log.prettyPrintLn("");
			p.prettyPrint();
		}
		Main.log.prettyPrintLn("begin");
		Main.log.prettyIndent();
		sList.prettyPrint();
		Main.log.prettyOutdent();

		Main.log.prettyPrintLn();
		Main.log.prettyPrint("end");
	}

	@Override public String identify() {
		return "<block> on line " + lineNum;
	}

	@Override void genCode(CodeFile f) {
		PascalDecl decl = (PascalDecl)context;
		for(ProcDecl p: pfDecl){
			//p.declLevel = decl.declLevel + 1;
			p.genCode(f);
		}
		f.genInstr(((PascalDecl)context).progProcFuncName, "", "", "");
		int numberOfVariables = 0;
		if(vDeclPart != null)
			numberOfVariables = vDeclPart.vDeclList.size();
		if(decl instanceof FuncDecl)//level of its block
			f.genInstr("", "enter", "$"+ (32+ (4 * numberOfVariables)) +",$" + (decl.declLevel+1), "Start of " + decl.name);
		else
			f.genInstr("", "enter", "$"+ (32+ (4 * numberOfVariables)) +",$" + decl.declLevel, "Start of " + decl.name);
		sList.genCode(f);
	}
}
