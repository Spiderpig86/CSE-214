import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * NOTE: THIS PROGRAM WAS WRITTEN BEFORE THE UPDATE SO THE PROGRAM WILL ASK
 * FOR AN OUTPUT PATH FOR THE FORMATTED CODE. THE FORMATTED CODE WILL BE
 * FORMATTED, PRINTED TO THE CONSOLE, AND PRINTED TO THE OUTPUT FILE. Esmaili
 * said this was ok.
 *
 * The driver class for the <code>JavascriptFormatter</code>.
 * The driver class will ask the user for the file that has
 * the code that needs to be formatted, the name of the file that
 * will be output, and then prints out the formatted code.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class JavascriptFormatterRunner {

    // Global scanner object.
    private static Scanner scanner = new Scanner(System.in);

    /**
     * The main method that starts the program. When called,
     * the user will prompted to enter the name of the file that needs
     * to be formatted and the output file name.
     *
     * @param args
     *      Default args for the main method.
     * @throws FileNotFoundException
     *      Thrown if the file specified by the user is not found.
     * @throws IOException
     *      Thrown if there was an error in I/O operations.
     */
    public static void main(String[] args)
            throws FileNotFoundException, IOException{
        try {
            displayGreeting();
            String inputPath = waitForFilePath();
            String outputPath = waitForOutputPath();
            JavascriptFormatter stack = new JavascriptFormatter();
            String code = stack.format(inputPath);
            stack.printToFile(outputPath, code);

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException f) {
            System.out.println("Error: An error occurred with I/O operations.");
        }
    }

    /**
     * Displays the greeting when the user first starts the program.
     */
    public static void displayGreeting() {
        System.out.println("Welcome to the Javascript Formatter");
    }

    /**
     * Prompts the user to enter the name of the file that needs to be
     * formatted and returns it.
     *
     * @return
     *      The name of the file in the current directory that needs
     *      to be formatted.
     */
    public static String waitForFilePath() {
        System.out.println("Please enter the name of the file you want to format:");
        String file = scanner.nextLine();

        if (file.isEmpty()) {
            file = waitForFilePath();
        }
        return file;
    }

    /**
     * Prompts the user to enter the name of the file that the formatted
     * code will be printed to.
     *
     * @return
     *      The name of the file in the current directory of where the formatted
     *      code will be output.
     */
    public static String waitForOutputPath() {
        System.out.println("Please enter the name of the output file:");
        String file = scanner.nextLine();

        if (file.isEmpty()) {
            file = waitForOutputPath();
        }
        return file;
    }
}
