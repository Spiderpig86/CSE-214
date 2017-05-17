import java.io.*;

/**
 * The <code>JavascriptFormatter</code> class handles the formatting
 * of the code from the file specified by the user. Key characters like
 * '{', '}', '(', ')', and the keyword 'for(' are pushed and popped
 * from the stack when needed to denote the structure of the code being
 * parsed. After scanning, the program will return certain error messages
 * if the criteria for errors are met.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class JavascriptFormatter {

    // The stack that will store the different BlockType enum constants.
    private final JSStack stack;
    private int indentLevel;

    private String unformattedCode = "", formattedCode = "", errorMessage = "";

    // Checks if the semicolon detected is part of the for loop.
    private boolean isInFor = false;

    // Variables that help keep track of the cursor location.
    // Also part of the extra credit.
    private int formattedLineCount = 1, formattedCurrentLoc = 1;

    // Location for flattened code.
    private int flattenedCurrentLoc = 1;

    /**
     * Constructs a new <code>JavascriptFormatter</code> object and
     * creates an empty stack.
     */
    public JavascriptFormatter() {
        stack = new JSStack();
    }

    /**
     * This method takes the data from the file and formats it.
     *
     * @param input
     *      The name of the file that needs to be read.
     *
     * <dt>Postcondition:
     *      <dd>The code is formatted and returned.</dd></dt>
     *
     * @return
     *      Returns the formatted version of the string with proper
     *      brace structure and indentation.
     *
     * @throws FileNotFoundException
     *      This exception is thrown if the file specified by the user is
     *      not found.
     *
     * @throws IOException
     *      There has been an error reading or writing the to the specified
     *      file.
     */
    public String format(String input) throws IOException,
        FileNotFoundException{
            readFile(input);
            if (!errorMessage.isEmpty()) {
                //System.out.println("// " + errorMessage);
                formattedCode += insertNewLine() + "//" + errorMessage;
            }
        return formattedCode;
    }

    /**
     * Reads the file specified by the user and flattens the code
     * being parsed by removing tabs and any extra spaces before parsing.
     * This reads line by line and appends it to the <code>unformattedCode
     * </code> variable.
     *
     * @param path
     *      The file name in the current directory that the user specifies
     *      that needs to be read.
     *
     * <dt>Postcondition:
     *      <dd>The specified file has been read and flattened where
     *      all line breaks and tab spaces have been removed. The unformatted
     *      code is stored in <code>unformattedCode</code>.</dd></dt>
     *
     * @throws FileNotFoundException
     *      This exception is thrown if the file specified by the user is
     *      not found.
     *
     * @throws IOException
     *      There has been an error reading or writing the to the specified
     *      file.
     */
    public void readFile(String path)
            throws FileNotFoundException, IOException {
            BufferedReader stdin = new BufferedReader(
                    new FileReader(path));

            String stuff;
            while ((stuff = stdin.readLine()) != null) {
                unformattedCode += stuff.replace("\t", "")
                        .trim().replaceAll(" +", " ");
                // Flattens code with no tabs and spaces greater than 1 with
                // a single space.
            }

            // Goes through the code to format.
            parseJS(unformattedCode);
    }

    /**
     * The function responsible for pushing and popping
     * '{', '}', '(', ')', and 'for(' representations of
     * <code>BlockType</code> enum constant in order to keep track
     * of the code's structure and detect any errors in the code's
     * format.
     *
     * <dt>Postcondition:
     *      <dd>The unformatted code has been parsed and formatted with
     *      the proper brace structure, spacing, and indentation.</dd></dt>
     *
     * @param code
     *      The unformatted version of the code that the program needs to
     *      parse.
     *
     * @return
     *      Returns the formatted version of the Javascript code with
     *      proper brace structure, parenthesis, line breaks, and indentation.
     */
    public String parseJS(String code) {

        char[] codeArray = code.toCharArray();

        for (int i = 0; i < codeArray.length; i++) {
            // This will skip over all the indices that have a for( so the
            // parser doesn't mistake "for(" with '('.
            if(i+4 < codeArray.length-1) {
                if(code.substring(i, i+4).equals("for(")) {
                    stack.push(BlockType.FOR);
                    i = (i + 4 < code.length()) ? i + 4 : i;
                    formattedCode+= "for(";
                    formattedCurrentLoc += 4;
                    isInFor = true;
                }
            }

            switch (codeArray[i]) {
                case '{':
                    stack.push(BlockType.BRACE);
                    indentLevel++;
                    formattedCode += codeArray[i];
                    formattedCode += insertNewLine();

                    for (int j = 0; j < indentLevel; j++) {
                        formattedCode += insertTab();
                    }
                    break;
                case '}':
                    try {
                        // Checks for what is next in the stack without
                        // actually removing it.
                        if (!stack.isEmpty() &&
                                stack.peek() != BlockType.BRACE) {
                            errorMessage = "Missing close parenthesis found."
                                    + getCurrentLocation();;
                            return formattedCode;
                        }
                        formattedCode += insertNewLine();
                        indentLevel--;
                        for (int j = 0; j < indentLevel; j++) {
                            formattedCode += insertTab();
                        }
                        // Prevents any extra new lines after close brace.
                        formattedCode += codeArray[i];
                        stack.pop();
                        if (i < codeArray.length - 1)
                            if (codeArray[i+1] != '}') {
                                formattedCode += insertNewLine();

                                for (int j = 0; j < indentLevel; j++) {
                                    formattedCode += insertTab();
                                }
                            }
                    } catch (EmptyStackException e) {
                        errorMessage = "Error: There is an extra ending" +
                                " curly brace in the code " +
                                getCurrentLocation();
                    }
                    break;
                case '(':
                    formattedCode += codeArray[i];
                    stack.push(BlockType.PAREN);
                    break;
                case ')':
                    try {
                        formattedCode += codeArray[i];
                        // Checks for what is next in the stack without
                        // actually removing it.
                        if (!stack.isEmpty() && stack.peek() != BlockType.PAREN
                                && stack.peek() != BlockType.FOR) {
                            errorMessage = "Error: There is an extra ending" +
                                    " parentheses " + getCurrentLocation();
                            return formattedCode;
                        }
                        stack.pop();
                        isInFor = false;
                    } catch (EmptyStackException e) {
                        errorMessage = "Error: There is an extra ending" +
                                " parentheses " + getCurrentLocation();
                    }
                    break;
                case ';':
                    formattedCode += codeArray[i];
                    if (i < codeArray.length - 1)
                        if (codeArray[i+1] == ')') {
                            errorMessage = "Error: A semicolon has been " +
                                    "detected in the wrong location."
                                    + getCurrentLocation();
                        }
                    // codeArray[i+1] != '}' prevents unneeded new line
                    // and spacing after a semicolon.
                    if (i < codeArray.length - 3)
                        if (!isInFor && codeArray[i+1] != '}') {
                            formattedCode += insertNewLine();
                        for (int j = 0; j < indentLevel; j++) {
                            formattedCode += insertTab();
                        }
                    }
                    break;
                default:
                    formattedCode += codeArray[i];
            }
            formattedCurrentLoc++;
            flattenedCurrentLoc++;
        }

        // Checks for any remaining elements.
        if (!stack.isEmpty()) {
            BlockType b = (BlockType) stack.peek();
            switch (b) {
                case BRACE:
                    errorMessage = "Missing close brace found " +
                            getCurrentLocation();
                    break;
                case PAREN:
                    errorMessage = "Missing close parenthesis found " +
                            getCurrentLocation();
                    break;
            }
        }
        return formattedCode;
    }

    /**
     * This prints or outputs the contents of the <code>formattedCode</code>
     * to a file on the disk specified by the user.
     *
     * @param outputPath
     *      The path where the file will be output.
     *
     * @param outputCode
     *      The cleaned up version of the code.
     *
     * <dt>Postcondition:
     *      <dd>The contents of the formatted code get printed into
     *      the user specified file.</dd></dt>
     *
     * @throws IOException
     *      Exception is thrown if there was an error in the input
     *      and output operations of the program.
     */
    public void printToFile(String outputPath, String outputCode)
            throws IOException {
        try {
            System.out.println(String.format("%6s", "").replace(" ", "-")
                + "Properly formatted program"
                    + String.format("%6s", "").replace(" ", "-"));
            File file = new File(outputPath);

            // Check if the file exists
            if (!file.exists())
                file.createNewFile();

            BufferedReader bReader = new BufferedReader(
                    new StringReader(outputCode));

            FileWriter fWriter = new FileWriter(file.getAbsoluteFile());
            PrintWriter pWriter = new PrintWriter(fWriter);

            // Write out all lines into the file.
            bReader.lines().forEach(pWriter::println);

            System.out.println(outputCode);
            pWriter.close();

            System.out.println(String.format("%2s", "").replace(" ", "-")
                    + "Thank you for making your code readable!"
                    + String.format("%2s", "").replace(" ", "-"));
            System.out.println("Exiting Program");
        } catch (IOException e) {
            System.out.println("Error: There has been an error in I/O" +
                    "operations.");
        }
    }

    /**
     * Returns a new line character and increments the line count and
     * cursor location.
     *
     * @return
     *      A new line character.
     *
     * <dt>Postcondition:
     *      <dd>A new line character is returned and the formattedLineCount
     *      and formattedCurrentLoc variables have been increased by 1.</dd></dt>
     */
    public String insertNewLine() {
        formattedLineCount++;
        formattedCurrentLoc = 1;
        return "\n";
    }

    /**
     * Returns a tab character and the formattedCurrentLoc variable has been
     * increased by 4 which represents the total number of spaces in
     * a tab character.
     *
     * @return
     *      Returns a tab character.
     */
    public String insertTab() {
        // Tabs are 4 spaces.
        formattedCurrentLoc += 4;
        return "\t";
    }

    /**
     * Returns a string representation of the location of where the cursor
     * currently is with its line and column number for both the formatted
     * and unformatted versions.
     *
     * @return
     *      Returns the string representation of where the cursor is in
     *      the code.
     */
    public String getCurrentLocation() {
        return "at line/col (formatted): " +
                Integer.toString(formattedLineCount) + "/" +
                Integer.toString(formattedCurrentLoc) +
                " and line/col (unformatted): 1/" +
                Integer.toString(flattenedCurrentLoc);
    }
}
