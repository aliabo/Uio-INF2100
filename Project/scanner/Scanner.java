package scanner;

import main.Main;
import static scanner.TokenKind.*;
import java.lang.*;
import java.io.*;

public class Scanner {
    public Token curToken = null, nextToken = null; 

    private LineNumberReader sourceFile = null;
    private String sourceFileName, sourceLine = "";
    private int sourcePos = 0;

    public Scanner(String fileName) {
	sourceFileName = fileName;
	try {
	    sourceFile = new LineNumberReader(new FileReader(fileName));
	} catch (FileNotFoundException e) {
	    Main.error("Cannot read " + fileName + "!");
	}

	readNextToken();  readNextToken();
    }


    public String identify() {
	return "Scanner reading " + sourceFileName;
    }


    public int curLineNum() {
	return curToken.lineNum;
    }

    
    private void error(String message) {
	Main.error("Scanner error on " +
		   (curLineNum()<0 ? "last line" : "line "+curLineNum()) + 
		   ": " + message);
    }

    public void readNextToken() {
	curToken = nextToken;  nextToken = null;
	String temp = "";
	int lineLength=sourceLine.length();
	// Del 1 her:
	// intialize
        while (nextToken == null){
		if(sourcePos >= lineLength ){//Reached end of line
			readNextLine();
			lineLength = sourceLine.length();
		}

	       skipSpaces(lineLength);
	       // code cut Token
	       temp = selectTokenText(temp,lineLength);// select the token from the text
	       temp = removeComments(temp,lineLength);// remove comments if found	
               lineLength = sourceLine.length();
	       temp = checkTwoTokens(temp);// check if there is 2 unsplitted Tokens
	       //skipSpaces(lineLength);
	       // First call of readNextToken()
	       checkIllegalSymbols(temp);// test
		if (curToken == null){ 
		    if(!temp.equals("")){
		    	nextToken = getToken(temp);
			temp = "";
		    } 
        	}
		// Second call of readNextToken()
		else{ 
		    // test of different Tokens
		    if(temp.length()>sourcePos){
			temp = testDifferentTokens(temp);
		     }
		     // not eof token
		     if(!temp.equals("")){
		    	nextToken = getToken(temp);//Token
			temp="";
		     }
		 }
		if(nextToken != null)
        		Main.log.noteToken(nextToken);
		else if (sourceLine.equals("")){
			nextToken = getToken("e-o-f");
			Main.log.noteToken(nextToken);
		}
	}
    }

    // A method to check if 2 tokens are included in one word
    private String checkTwoTokens(String s){
	if(s.length()>1 && containsToken(s.charAt(s.length()-1)+"")){
		s = s.substring(0,s.length()-1);
		if(sourcePos>1)
			sourcePos -= 1; //move the cursor before the tokenat last position 
		else sourcePos =0; 
	}
	return s;
    }

    private void checkIllegalSymbols(String s){
	if(s.equals(""))
		return;
	if(s.charAt(0) == '\'' || s.charAt(0) == '’')
		return;
	// TODO check the illegal symbols
	else if(s.contains("!") || s.contains("?") || s.contains("@") || s.contains("!") || s.contains("|") || s.contains("^"))
		scannerError("Illegal symbol!");
    }

    // A method to skip spaces
    private void skipSpaces(int lineLength){
	if(!sourceLine.equals("")){	
		while(sourcePos < lineLength && sourceLine.charAt(sourcePos) == ' '){
			sourcePos++;
			break;
		}
	}
	
    }
	
    // A method to select the part of line that contains a token
    private String selectTokenText(String s, int lineLength){
 	while(!containsToken(s) && sourcePos < lineLength){
		s = removeComments(s,lineLength);	
                lineLength = sourceLine.length();
		// seeking token
		if(sourceLine.charAt(sourcePos) != ' ' && sourceLine.charAt(sourcePos) != '\n' ){
			s += Character.toLowerCase(sourceLine.charAt(sourcePos));
			sourcePos++;
		}
	}
	return s;
    }

    // A method that calls the method checkNextchar for different values
    private String testDifferentTokens(String s){

	s = checkNextchar(s,':',sourceLine.charAt(sourcePos+1),'=');
	s = checkNextchar(s,'.',sourceLine.charAt(sourcePos+1),'.');
        s = checkNextchar(s,'>',sourceLine.charAt(sourcePos+1),'=');
	s = checkNextchar(s,'<',sourceLine.charAt(sourcePos+1),'=');
	s = checkNextchar(s,'<',sourceLine.charAt(sourcePos+1),'>');
	return s;
    }

    // A method for look for comments and remove them
    private String removeComments(String s,int lineLength){

	// Remove comments
	if (s.contains("/*")){
		s= s.substring(0,s.length()-2); // to remove /*
		while(((sourceLine.charAt(sourcePos)!='/') || (sourceLine.charAt(sourcePos-1) != '*')) && (!sourceLine.equals(""))){
			//new line				
			if(sourcePos == lineLength){
				readNextLine();
				lineLength=sourceLine.length();
				continue;
			}
			sourcePos++;
		}
		// error  */
		if(sourceLine.equals("")){
			scannerError("*/");
                }
		else sourcePos++;
	}
	// removing comments
	else if (s.contains("{")){
		s= s.substring(0,s.length()-1); // to remove /*
		while(sourceLine.charAt(sourcePos)!='}' && sourcePos != lineLength)
			sourcePos++;
		// Error not found }
		if(sourceLine.charAt(sourcePos)!='}' )
			scannerError("*/");
		else sourcePos++;
	}
	return s;

    }
    private String checkNextchar(String s,char c, char nextC,char c2){

	if(s.equals(c) && nextC==c2){
		sourcePos++;
		return s += c2;
	    }
	return s;
    }
    private boolean containsToken(String s){

      for (TokenKind k: TokenKind.values()){
           if(s.contains(k.toString()))
		return true;
	}
       return false;
    }

    // This method finds the appropriate TokenKind
    private Token getToken(String s){
        Token token = TokenKind.getToken(s,getFileLineNum());
	if(token == null && !s.equals("")){ //check general tokenkinds
		if(s.charAt(0)> '0' && s.charAt(0) < '9'){ // int
			try{
	   			token = new Token(Integer.parseInt(s),getFileLineNum());
			}catch(Exception e){
				scannerError("int");
			}
		}
		else if(s.charAt(0) == '\''){
			if(s.charAt(2) == '\'' ){
				token = new Token(s.charAt(1),getFileLineNum());
			}
			else{//error
				scannerError("'");
			}
		}
		else{
			token = new Token(s,getFileLineNum());
		}
	}
	return token;
    }
    private void readNextLine() {
	if (sourceFile != null) {
	    try {
		sourceLine = sourceFile.readLine();
		if (sourceLine == null) {
		    sourceFile.close();  sourceFile = null;
		    sourceLine = "";  
		} else {
		    sourceLine += " ";
		}
		sourcePos = 0;
	    } catch (IOException e) {
		Main.error("Scanner error: unspecified I/O error!");
	    }
	}
	if (sourceFile != null) 
	    Main.log.noteSourceLine(getFileLineNum(), sourceLine);
    }


    private int getFileLineNum() {
	return (sourceFile!=null ? sourceFile.getLineNumber() : 0);
    }


    // Character test utilities:

    private boolean isLetterAZ(char c) {
	return 'A'<=c && c<='Z' || 'a'<=c && c<='z';
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }
    public void scannerError(String message) {
	Main.error(curLineNum(), 
		   "Expected a " + message);
    }

    // Parser tests:

    public void test(TokenKind t) {
	if (curToken.kind != t)
	    testError(t.toString());
    }

    public void testError(String message) {
	Main.error(curLineNum(), 
		   "Expected a " + message +
		   " but found a " + curToken.kind + "!");
    }

    public void skip(TokenKind t) {
	test(t);  
	readNextToken();
    }
}
