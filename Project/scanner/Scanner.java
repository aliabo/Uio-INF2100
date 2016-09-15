package scanner;

import main.Main;
import static scanner.TokenKind.*;

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
	int lineLength=0;
	// Del 1 her:
	// intialize
	if(sourcePos == 0 ){
		readNextLine();
		lineLength = sourceLine.length();
	}
	
	
	// kode cut Token
	while(!containsToken(temp) && sourcePos < lineLength){//eof?

		temp = removeComments(temp,lineLength);	
                lineLength = sourceLine.length();
		// seeking token
		if(sourceLine.charAt(sourcePos) != ' ' && sourceLine.charAt(sourcePos) != '\n' ){
			temp += sourceLine.charAt(sourcePos);
			sourcePos++;
		}
		// Escaping spaces
		else{
			sourcePos++;
			break;
		}
	}
	 // Perparing for reading a newLine
	 if(sourcePos >= lineLength){
		sourcePos = 0;
         }
	 // First call of readNextToken()
	 if (curToken == null){ 
	    nextToken = getToken(temp);
         }
	 // Second call of readNextToken()
	 else{ 
	    // test of different Tokens
	    temp = checkNextchar(temp,':',sourceLine.charAt(sourcePos+1),'=');
	    temp = checkNextchar(temp,'.',sourceLine.charAt(sourcePos+1),'.');
	    temp = checkNextchar(temp,'>',sourceLine.charAt(sourcePos+1),'=');
	    temp = checkNextchar(temp,'<',sourceLine.charAt(sourcePos+1),'=');
	    temp = checkNextchar(temp,'<',sourceLine.charAt(sourcePos+1),'>');
	  // not eof token
	  if(nextToken != null)
	    	nextToken = getToken(temp);//Token
	  else{
		nextToken = getToken("e-o-f");
	  }
            //compare with curToken
	 }
	if(nextToken != null)
        	Main.log.noteToken(nextToken);
    }

    // A function for look for comments and remove them
    private String removeComments(String s,int lineLength){

	// Remove comments
	if (s.contains("/*")){
		s= s.substring(0,s.length()-2); // to remove /*
		while(((sourceLine.charAt(sourcePos)!='/') || (sourceLine.charAt(sourcePos-1) != '*')) && (sourceLine != null)){
			
			//new line				
			if(sourcePos == lineLength){
				readNextLine();
				lineLength=sourceLine.length();
				continue;
			}
			sourcePos++;
		}
		// error  */
		if(sourceLine == null){
			scannerError("*/");
                }
		else{
			sourcePos++;
		}
	}
	// removing comments
	else if (s.contains("{")){
		s= s.substring(0,s.length()-1); // to remove /*
		while(sourceLine.charAt(sourcePos)!='}' && sourcePos != lineLength)
			sourcePos++;
		// Error not found }
		if(sourceLine.charAt(sourcePos)!='}' )
			scannerError("*/");
		else{
			sourcePos++;
		}
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
    private boolean containsToken(String s){//toString()?

      for (TokenKind k: TokenKind.values()){
           if(s.equals(k))
		return true;
	}
       return false;
    }

    // This method finds the appropriate TokenKind
    private Token getToken(String s){//TODO check integer og variables

	return TokenKind.getToken(s,getFileLineNum());
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
