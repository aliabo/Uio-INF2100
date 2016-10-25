package scanner;
import main.Main;
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

	/**
	 * The control method, that being called when want to read next line
	 * Is also the control method for the whole class.
	 * <p>
	 * Use of methods:
	 * if end of line update with new line:				{@link #reachedEndOfLine}
	 * we skip spaces and tabs: 						{@link #skipSpaces}
	 * select the tokens from the source line: 			{@link #selectTokenText}
	 * remove comments if found: 						{@link #removeComments}
	 * check if there is 2 not-slitted Tokens like i: 	{@link #checkTwoTokens}
	 * Check so that symbols is not present: 			{@link #checkIllegalSymbols}
	 * First call to readNextToken:						{@link #firstCallToReadNextToken}
	 * Second call to readNextToken: 					{@link #secoundCallToReadNextToken}
	 * If still tokens, log, else, e-o-f:				{@link #logEndOfToken}
	 *<p>
	 */
	public void readNextToken() {
		curToken = nextToken;  nextToken = null;
		String temp = "";
		int lineLength = currentLineLength();
		while (nextToken == null){
			lineLength = reachedEndOfLine(lineLength);
			skipSpaces(lineLength);
			temp = selectTokenText(temp,lineLength);
			temp = removeComments(temp,lineLength);
			lineLength = currentLineLength();
			temp = checkTwoTokens(temp);
			checkIllegalSymbols(temp);

			if (curToken == null) {
				temp = firstCallToReadNextToken(temp);

			}else if(moreToRead(lineLength)) {
				temp = secoundCallToReadNextToken(lineLength, temp);
			}
			logEndOfToken();
		}
	}

	/**
	 * Method to update lineLength
	 *<p>
	 * @return 	lineLength		 updated value
	 */
	private int currentLineLength(){
		return sourceLine.length();
	}

	/**
	 * A method to skip spaces and tabs in code
	 *<p>
	 * @param 	lineLength		length of incoming text
	 */
	private void skipSpaces(int lineLength){
		if(!sourceLineEmpty()){
			while(moreToRead(lineLength)
					&& (charAtPositionIs() == ' ' || charAtPositionIs() == '\t')){
				sourcePos++;
				break;
			}
		}
	}
	
	/**
	 * Test if still more to read
	 * <p>
	 * @return boolean 		True if empty, False otherwise
	 */
	private boolean moreToRead(int lineLength){
	 	return sourcePos < lineLength;
	}

	/**
	 * Test if string is empty
	 * <p>
	 * @return boolean 		True if empty, False otherwise
	 */
	private boolean sourceLineEmpty(){
		return sourceLine.equals("");
	}

	/**
	 * What char we have at this position?
	 * <p>
	 * @return char 	char a specified position
	 */
	private char charAtPositionIs(){
		return sourceLine.charAt(sourcePos);
	}

	/**
	 * TA method to select the part of line that contains a token
	 * As long as their is no space and no new line we look for token
	 *
	 * <p>
	 * @param s				String to update
	 * @param lineLength    to update current lineLength
	 * @return boolean 		the updated String value
	 */
	private String selectTokenText(String s, int lineLength){
		while(!containsToken(s) && moreToRead(lineLength) && charAtPositionIs() != ' ' && charAtPositionIs() != '\t'){
			s = removeComments(s, lineLength);
			lineLength = currentLineLength();
			if(charAtPositionIs() != ' ' && charAtPositionIs() != '\n' && charAtPositionIs() != '\t'){
				if(charAtPositionIs() == '\''){
					return specialCaseOfReadingChar(lineLength, s);
				}
				s += Character.toLowerCase(sourceLine.charAt(sourcePos));
				sourcePos++;
			}
		}
		return s;
	}

	/**
	 * Were looking for special case of chars
	 * Try and catch if Illegal char literal
     *
	 * <p>
	 * @param s				String to update
	 * @param lineLength    to update current lineLength
	 * @return out 			the updated String value
	 */
	private String specialCaseOfReadingChar(int lineLength, String s){
		String out = s;
		try{
			if(s.equals("")){
				if(sourcePos + 4 < lineLength)
					if(sourceLine.substring(sourcePos, sourcePos + 4).equals("''''")){//''''
						s += sourceLine.substring(sourcePos,sourcePos + 3);
						sourcePos += 4;
						out = s;
					}
				s += sourceLine.substring(sourcePos,sourcePos + 3);
				sourcePos += 3;
			}
			out = s;
		}catch(Exception e){
			scannerError("Illegal char literal!");
		}
		return out;
	}

	/**
	 * A method to check if a word contains a token
	 *<p>
	 * @param 	s		the location of the token, relative to the s argument
	 * @return 	boolean		True if Token found, False otherwise
	 */
	private boolean containsToken(String s){
		if(sourcePos < sourceLine.length())
			// a token is a part of a name
			if(sourcePos > 0)
               			if(isLetterAZ(sourceLine.charAt(sourcePos)) && isLetterAZ(sourceLine.charAt(sourcePos-1)))
					return false;
			for (TokenKind k: TokenKind.values()){
				if(s.contains(k.toString()))
					return true;
			}
		return false;
	}

	/**
	 * A method for look for comments in program and remove them, not used as tokens
	 * They are either identified as "/*" or "{"
	 * <p>
	 * If s contains "/*" we skip to 2 else if s contains "{" we skip 1, se more
	 * at the use of:	    {@link #readNewLine}
	 * if line is empty:    {@link #noEndtestIfEmpty}
	 *
	 *
	 * @param 	s	the location of the token, relative to the s argument
	 * @param lineLength 	length of the incoming source
	 * @return 	s	Returned value
	 */
	private String removeComments(String s, int lineLength){
		int nrOfCharToRemove;
                boolean success = false;
		if (s.contains("/*")){
			nrOfCharToRemove = 2;
			s = s.substring(0,s.length() - nrOfCharToRemove);
			while((!sourceLineEmpty()) && moreToRead(lineLength) && ((charAtPositionIs() != '/') || (sourceLine.charAt(sourcePos-1) != '*'))){
                                sourcePos++;
				lineLength = readNewLine(lineLength, nrOfCharToRemove);

				if(sourcePos>0){
				    if(charAtPositionIs() == '/' && sourceLine.charAt(sourcePos-1) == '*')
                                        success = true;
                                }else{
                                     sourcePos++;
                                }
			}
			if (!success){
				scannerError("No end for comment starting on line " + curLineNum() + "!");
                        }else{
				sourcePos++;
                        }

		} else if (s.contains("{")){
			nrOfCharToRemove = 1;
			s = s.substring(0, s.length() - nrOfCharToRemove);
			while((!sourceLineEmpty()) && moreToRead(lineLength) && (charAtPositionIs() != '}')){
                                sourcePos++;
				lineLength = readNewLine(lineLength, nrOfCharToRemove);
                                if(charAtPositionIs() == '}')
                                    success = true;
                                
			}
			// error  */
                        if (!success){
				scannerError("No end for comment starting on line " + curLineNum() + "!");
                        }else{
				sourcePos++;
                        }
		}
		return s;
	}


	/**
	 * A method skip lines that don't contain 2 chars for "/*"
	 * And skip lines that don't contain 1 chars for }  for "{"
	 *
	 * @param nrOfCharToRemove 	what kind of comments we are looking at
	 * @param lineLength 		length of the incoming source
	 * @return linelength		Returned value
	 */
	private int readNewLine(int lineLength, int nrOfCharToRemove) {
		if (sourcePos == lineLength) {
			readNextLine();
			lineLength = currentLineLength();
			while (sourceLine.length() < nrOfCharToRemove && !sourceLineEmpty()) {
				readNextLine();
				lineLength = currentLineLength();
			}
                        if (sourceLineEmpty())
                           lineLength = 0;
		}
		return lineLength;
	}

	/**
	 * A method to check if 2 tokens are included in one word
	 * <p>
	 * This method checks if the incoming string s length is bigger then 1
	 * and:	{@link #containsToken}
	 * and s not a char
	 * if the sourchPos is bigger then 1; move the cursor before the tokens last position
	 *
	 * @param 	s		the location of the token, relative to the s argument
	 * @return 	s		Returned value
	 */
	private String checkTwoTokens(String s){
		if((s.length() > 1)
				&& containsToken(s.charAt(s.length() - 1) + "")
				&& (s.charAt(0) != '\'')){

			s = s.substring(0, s.length()-1);
			if(sourcePos > 1)
				sourcePos -= 1;
			else sourcePos =0;
		}
		return s;
	}

	/**
	 * If s is empty, return, if char literal, return, else
	 * <p>
	 * This Method calls the method {@link #scannerError}, for different
	 * illegal values.
	 * Checks if found, and let scannerError throw an appropriate message
	 *
	 * @param 	s		the location of the token, relative to the s argument
	 */
	private void checkIllegalSymbols(String s){
		if(s.equals(""))
			return;
		if(s.charAt(0) == '\'')
			return;
			//  check the illegal symbols
		else if(s.contains("!"))
			scannerError("Illegal character: '!'!");
		else if (s.contains("#"))
			scannerError("Illegal character: '#'!");
		else if (s.contains("%"))
			scannerError("Illegal character: '%'!");
		else if (s.contains("&"))
			scannerError("Illegal character: '&'!");
		else if (s.contains("/"))
			scannerError("Illegal character: '/'!");
		else if (s.contains("?"))
			scannerError("Illegal character: '?'!");
		else if (s.contains("\""))
			scannerError("Illegal character: '\"'!");
		else if (s.contains("'"))
			scannerError("Illegal character: '''!");
		else if (s.contains("_"))
			scannerError("Illegal character: '_'!");
	}

	/**
	 * Method to update lineLength and go to next line if end found
	 *<p>
	 *
	 * @param 	lineLength		length of incoming text
	 * @return 	lineLength		 updated value
	 */
	private int reachedEndOfLine(int lineLength){
		if(sourcePos >= lineLength ){
			readNextLine();
			lineLength = currentLineLength();
		}
		return lineLength;
	}

	/**
	 * Method to call our first {@link #readNextLine}
	 *<p>
	 *
	 * @param temp 		our actual textline
	 * @return temp		updated source
	 */
	private String firstCallToReadNextToken(String temp) {
		if (!temp.equals("")) {
			nextToken = getToken(temp);
			temp = "";
		}
		return temp;
	}

	/**
	 * Method to call our second {@link #readNextLine}
	 * first test of different Tokens {@link #testDifferentTokens}
	 * like : or :=
	 * If not eof, proceed
	 *<p>
	 *
	 * @param 	temp 	our actual textline
	 * @return 	temp	updated source
	 */
	private String secoundCallToReadNextToken(int lineLength, String temp){
		if(moreToRead(lineLength)){
			temp = testDifferentTokens(temp);
		}
		if(!temp.equals("")){
			nextToken = getToken(temp);
			temp="";
		}
		return temp;
	}

	/**
	 * Returns the next char for different values
	 * <p>
	 * This method that calls the method {@link #checkNextchar}, for different values
	 *
	 * @param 	s		the location of the token, relative to the s argument
	 * @return  s		found match is returned
	 */
	private String testDifferentTokens(String s){
		s = checkNextchar(s, ':', sourceLine.charAt(sourcePos), '=');
		s = checkNextchar(s, '.', sourceLine.charAt(sourcePos), '.');
		s = checkNextchar(s, '>', sourceLine.charAt(sourcePos), '=');
		s = checkNextchar(s, '<', sourceLine.charAt(sourcePos), '=');
		s = checkNextchar(s, '<', sourceLine.charAt(sourcePos), '>');
		return s;
	}

	/**
	 * Returns the next char for different values
	 * <p>
	 * This method is used to choose tokens like (: or :=) (< or <=)
	 *
	 * @param 	s		the location of the token, relative to the s argument
	 * @param 	c   	char we are looking for
	 * @param 	c2		char we are looking for
	 * @param 	nextC 	position of char to test
	 * @return  s		found match is returned
	 */
	private String checkNextchar(String s, char c, char nextC, char c2){
		if(s.equals(c + "") && nextC == c2){
			sourcePos++;
			s += c2;
			return s;
		}
		return s;
	}

	/**
	 * Method, that if next token is not null, log it
	 * if line is empty, end of token
	 *<p>
	 *
	 */
	private void logEndOfToken(){
		if(nextToken != null) {
			Main.log.noteToken(nextToken);

		}else if(sourceLineEmpty()) {
			nextToken = getToken("e-o-f");
			Main.log.noteToken(nextToken);
		}
	}

	/**
	 * Returns an appropriate TokenKind
	 * <p>
	 * This method checks for general tokens. numbers and if its a char literal
	 * This method uses {@link #isDigit(char)}, to get Ints
	 * and {@link #tryCatchInt}, Try/catch Int
	 * and {@link #tryCatchChar}, Try/catch Char
	 *
	 * @param  	s	the location of the token, relative to the s argument
	 * @return  token	the token of the specified token
	 * @see		Token
	 */
	private Token getToken(String s){
		Token token = TokenKind.getToken(s, getFileLineNum());

		if(token == null && !s.equals("")){
			if(isDigit(s.charAt(0))) {
				token = tryCatchInt(token, s);

			}else if(s.contains("\'")){
				token = tryCatchChar(token, s);

			}// end else if
			else{
				token = new Token(s, getFileLineNum());
			}
		}
		return token;
	}

	/**
	 * Returns token if no error
	 * <p>
	 * This method checks for int
	 * Scanner error; {@link #scannerError}
	 *
	 * @param  	s		the location of the token, relative to the s argument
	 * @return  token	the token of the specified token
	 */
	private Token tryCatchInt(Token token, String s){
		try{
			token = new Token(Integer.parseInt(s), getFileLineNum());
		}catch(Exception e){
			scannerError("Expected int!");
		}
		return token;
	}

	/**
	 * Returns token if no error
	 * <p>
	 * This method checks for char
	 * Scanner error; {@link #scannerError}
	 *
	 * @param  	s		the location of the token, relative to the s argument
	 * @return  token	the token of the specified token
	 */
	private Token tryCatchChar(Token token, String s){
		try{
			if(s.charAt(0) == '\''  && s.charAt(2) == '\'' ){
				token = new Token(s.charAt(1), getFileLineNum());
			}else{scannerError("Illegal char literal!");}

		}catch(Exception e){
			scannerError("Illegal char literal!");
		}
		return token;
	}

	/**
	 * Error message is displayed
	 * {@link main.LogFile} for noteError
	 * Make a note in the log file that an error has occured.
	 * (If the log file is not in use, request is ignored.)
	 *
	 * @param message String to save in log
	 */
	private void error(String message) {
		Main.error("Scanner error" +
				(getFileLineNum()>0 ? " on line "+getFileLineNum() : "") +
				": " + message);
	}

	// read next line in file
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

	/**
	 * If sourceFile is not null, we return the lineNumber
	 *
	 * @return int LineNumber
	 */
	private int getFileLineNum() {
		return (sourceFile!=null ? sourceFile.getLineNumber() : 0);
	}

	/**
	 * Is the incoming character an letter
	 *
	 * @param c		Character to check
	 * @return		returns True if found, False otherwise
	 */
	private boolean isLetterAZ(char c) {
		return 'A'<=c && c<='Z' || 'a'<=c && c<='z';
	}


	/**
	 * Is the incoming character an digit
	 *
	 * @param c		Character to check
	 * @return		returns True if found, False otherwise
	 */
	private boolean isDigit(char c) {
		return '0'<=c && c<='9';
	}


	public void scannerError(String message) {
		Main.error(curLineNum(), message);
	}

	// Parser tests:
	/**
	 * Test if incoming Token t is specified Token
	 * We need to test, if class is a subclass of {@link parser.ParamDecl}
	 *
	 * @param t 	token to test
	 */
	public void test(TokenKind t) {
		if (curToken.kind != t)
			testError(t.toString());
	}

	/**
	 * If we dont find what we are looking for in the current Token
	 *
	 * @param message	String of expected outcome
	 */
	public void testError(String message) {
		Main.error(getFileLineNum(),
				"Expected a " + message +
						" but found a " + curToken.kind + "!");
	}

	/**
	 * {@link #test}
	 * then we do another call to readNextToken()
	 *
	 * @param t     token to test
     */
	public void skip(TokenKind t) {
		test(t);
		readNextToken();
	}
}
