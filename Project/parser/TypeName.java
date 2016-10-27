package parser;
import main.*;
import scanner.*;

public class TypeName extends Type{

    public String name;
    types.Type type;
    TypeName(int lNum) {
        super(lNum);
    }

    /**
     * Parser method to declare the language, explained as a rail-diagram; TypeName
     *
     * {@link package.main.log.enterParser} Make a note that the parser has started parsing a non-terminal.
     *
     * --> [name] -->
     * If not empty, get curToken id, else toString
     * readNextToken()
     *
     * {@link package.main.log.enterParser} Make a note that the parser has finished parsing a non-terminal.
     *
     * @param s     is the Scanner object, of the token that the is the scanners current Token read,
     *              s.skip(), send it to specific parser [non - terminal]
     *
     * @return type  object TypeName
     */
    public static TypeName parse(Scanner s) {
        enterParser("type name");
        TypeName t = new TypeName(s.curLineNum());

        if(s.curToken.id != null){
            t.name = s.curToken.id;
        }else{
            t.name = s.curToken.kind.toString();
        }

        s.readNextToken();
        leaveParser("type name");
        return t;
    }

    @Override void prettyPrint() {
        Main.log.prettyPrint(name);
    }

    @Override void check(Block curScope, Library lib) {
        TypeDecl d = (TypeDecl) curScope.findDecl(name, this);
        type = d.type;
    }

    @Override public String identify() {
        return "<type name> on line " + lineNum;
    }
}
