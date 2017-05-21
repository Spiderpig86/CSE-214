import jdk.internal.util.xml.impl.Input;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main class in which the program will run where the user input for some
 * properties such as the number of servers, download speed, duration,
 * premium job probability, and regular job probability are asked. It then
 * constructs an instance of <code>DownloadSchedule</code> which will run the
 * simulation with the given inputs.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class DownloadManager {

    // Class global scanner object
    //private static Scanner scanner = new Scanner(System.in);

    // Instance fields for the simulation
    private static int serverNum = 0;
    private static int dlSpeed = 0;
    private static int duration = 0;
    private static double premiumProb = 0.0;
    private static double regularProb = 0.0;

    /**
     * The method that will take in user input that will specify the
     * arguments of the <code>DownloadJob</code> object for the simulation.
     *
     * @param args
     *      Standard args for the main method.
     *
     */
    public static void main(String[] args) {
        displayHeading();

        serverNum = waitForNum("Please enter number of servers:");
        dlSpeed = waitForNum("Please enter download speed:");
        duration = waitForNum("Please enter length of time:");
        premiumProb = waitForProb("Please enter a probability of new premium " +
                "jobs per timestep:");
        regularProb = waitForProb("Please enter a probability of new regular " +
                "jobs per timestep:");

        DownloadScheduler dlScheduler = new DownloadScheduler(premiumProb,
                regularProb, serverNum, duration, dlSpeed);

        System.out.println(dlScheduler.simulate()); // Generates and outputs
        // simulation data.
    }

    // HELPER METHODS

    /**
     * The method that will handle user input for integers such as the number
     * of servers, download speed, and duration for downloads.
     *
     * @param message
     *      The message associated with entering the number.
     *
     * @return
     *      The integer that the user entered.
     */
    public static int waitForNum(String message) throws
        IllegalArgumentException, InputMismatchException {
        int returnNum = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println(message);

            if (scanner.hasNext()) {
                returnNum = scanner.nextInt();
            }

            if (returnNum < 0)
                throw new IllegalArgumentException("Error: You must enter a " +
                        "positive integer.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return waitForNum(message);
        } catch (InputMismatchException e) {
            System.out.println("Please enter a nonnegative integer.");
            return waitForNum(message);
        }
        return returnNum;
    }

    /**
     * The method that will handle user input for doubles which are used in
     * the probability for <code>DownloadJobs</code> both premium and regular.
     *
     * @param message
     *      The command for the user input.
     *
     * @return
     *      Returns the double that the user entered.
     *
     */
    public static double waitForProb(String message)
            throws IllegalArgumentException, InputMismatchException {
        Scanner scanner = new Scanner(System.in);

        double returnProb = 0.0;

        try {
            System.out.println(message);
            if (scanner.hasNext()) {
                returnProb = scanner.nextDouble();
            }

            if (returnProb < 0.0) // Maybe check if it is also greater than 1.
                throw new IllegalArgumentException("Error: Pleas enter a " +
                        "value between 0 and 1.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return waitForProb(message);
        } catch (InputMismatchException e) {
            System.out.println("Please enter a nonnegative decimal.");
            return waitForProb(message);
        }

        return returnProb;
    }

    /**
     * Displays the heading of the program.
     */
    public static void displayHeading() {
        System.out.println("Hello and welcome to the download scheduler.\n");
    }
}
