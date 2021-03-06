package parser;
import main.*;
import scanner.Scanner;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class ProcCallStatm extends Statement {

    private ArrayList<Expression> expList = new ArrayList<>();
    String procName;
    ProcDecl procRef;
    TypeDecl writeRef = null;

    ProcCallStatm(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; ProcCallStatm
     * functions always return a value
     *
     * Make a note that the parser has started parsing a non-terminal.
     *
     * Z' == 0, in combination with '*' and '?'
     * 'One' == 1, in combination with '*' and '?'
     * '?' == 0 or 1 (indicates that after this '?' symbol, it can be 0 or 1 terminal)
     * '*' == or many (indicates that after this '*' symbol, it can be 0 or many terminal)
     *
     * -- [name] -- Z  ( '(' ) -- One [expression] * -- ( , )  ? --
     *
     * Special condition, name; we use {@link package.test} if nametoken (else testError)
     * we also need to update, so a call for {@link package.readNextToken}
     * if we have a ' ( ' we also have a [non-terminal]
     * s.skip(), [non-terminal]
     *
     * Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return pc  object ProcCallStatm
     */
    public static ProcCallStatm parse(Scanner s) {
        enterParser("proc call");
        s.test(nameToken);
        ProcCallStatm pc = new ProcCallStatm(s.curLineNum());
        pc.procName = s.curToken.id;
        s.readNextToken();

        if(s.curToken.kind == leftParToken) {

            s.skip(leftParToken);
            pc.expList.add(Expression.parse(s));

            while(s.curToken.kind == commaToken){
                s.skip(commaToken);
                pc.expList.add(Expression.parse(s));
            }
            s.skip(rightParToken);
        }
        //pc.name = TypeName.parse(s);
        leaveParser("proc call");
        return pc;
    }

    /**
     * Abstract code beautifiers, inherited from PascalSyntax -- Statment
     *
     * NameToken, if expList not null we print and remove
     * List is empty, new line
     * Print all in list
     *
     * Calls the logFile {@link package.main.log.prettyPrint}, an formatting conventions
     * that adjust positioning and spacing (indent style), to make the content easier for other
     * programmers to view, read, and understand.
     */
    @Override void prettyPrint() {
        Main.log.prettyPrint(procName);
        if (expList.size() >0) {
            Main.log.prettyPrint("(");

            expList.get(0).prettyPrint();

	    for(int i = 1; i<expList.size();i++){
                Main.log.prettyPrint(", ");
                expList.get(i).prettyPrint();
            }
            Main.log.prettyPrint(")");
        }
    }

    /**
     * Suppose name exists in the local block:
     * Therefore we are sending a pointer to it as parameter to check.
     * checkWhetherProcedure(this);
     * If d is an instance of Type Decl, procedure write
     * cast to ProcDecl, check if parameter is the same.
     * and check if the parameters have the same type
     *
     * @param curScope  current scope
     * @param lib       library (bind)
     */
    @Override void check(Block curScope, Library lib) {
	     PascalDecl d = curScope.findDecl(procName,this);
	     d.checkWhetherProcedure(this);
	     if(d instanceof TypeDecl){// procedure write
		       writeRef = (TypeDecl)d;
		       for (Expression exp: expList)
			        exp.check(curScope,lib);
		       return;
	     }
	     procRef = (ProcDecl)d;
	     if(expList.size()!=0){
		       if(expList.size() > procRef.pList.pList.size())
				       error("Too many parameters in call on " + procRef.name + "!");
		       else if (expList.size() < procRef.pList.pList.size())
				       error("Too few parameters in call on " + procRef.name + "!");
		       for(int i = 0; i < expList.size(); i++){
			          // checking that parameters has same type as in declaration of function
			          Expression procCallParam = expList.get(i);
			          ParamDecl procDeclParam = procRef.pList.pList.get(i);
			          procCallParam.check(curScope,lib);
			          procCallParam.type.checkType(procDeclParam.type,"param #" + (i+1), this,
                    	"Illegal type of parameter #"+ (i+1) + "!");
		       }
	    }
     }

    @Override public String identify() {
        return "<proc call> on line " + lineNum;
    }

    /**
     * Function were we handle the case of procedure write: for char int and bool.
     *
     * @param f     Code file to be generated after traversing the tree
     */
    @Override void genCode(CodeFile f) {
       // write
	    if(writeRef != null){// procedure write
		      for (Expression exp: expList){
               exp.genCode(f);
               f.genInstr("", "pushl", "%eax", "Push next param.");
			         if(exp.type instanceof types.CharType)
			            f.genInstr("", "call", "write_char", "");
               else if (exp.type instanceof types.IntType)
                  f.genInstr("", "call", "write_int", "");
               else if (exp.type instanceof types.BoolType )
                  f.genInstr("", "call", "write_bool", "");
			         f.genInstr("", "addl", "$4,%esp", "Pop param.");
		    }
			 //TODO
			 //case lib.
	    }
	    else{
          for (int i = expList.size() -1 ; i >=0 ;i--){
              expList.get(i).genCode(f);
              f.genInstr("", "pushl", "%eax", "Push param #" + (i + 1) + ".");
	        }
          f.genInstr("", "call", procRef.progProcFuncName,"");
          if(expList.size() != 0)
            f.genInstr("", "addl", "$" + (4 * expList.size()) + ",%esp", "Pop params.");
      }
    }
}
