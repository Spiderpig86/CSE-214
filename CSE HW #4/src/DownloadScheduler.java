/**
 * This class simulates a series of user set download jobs with a given
 * probability and duration and then prints out the summary of each timestep
 * and the summary for the series of jobs as a whole.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class DownloadScheduler {

    // Instance Fields used for the main program execution itself.
    private DownloadQueue regularQ;
    private DownloadQueue premiumQ;

    private int currentTime = 0;
    private int simulationEndTime;
    private DownloadRandomizer dlRandomizer;
    private DownloadJob[] currentJobs;

    private int currentId = 1;

    private int downloadSpeed = 0;

    // Used in loops to enqueue new jobs to the queues if allowed
    private String newPremJob = "n/a";
    private String newRegJob = "n/a";

    // Instance fields needed for the summary
    private int premiumJobCount = 0;
    private int regularJobCount = 0;
    private int premiumData = 0;
    private int regularData = 0;
    private int premiumTotalWait = 0;
    private int regularTotalWait = 0;


    /**
     * The constructor for a <code>DownloadScheduler</code> which takes in
     * parameters to help set up the simulation.
     *
     * @param premiumProb
     *      The probability of generating premium <code>DownloadJobs</code>
     *      which have priority over regular <code>DownloadJobs</code>.
     * @param regularProb
     *      The probability of generating regular <code>DownloadJobs</code>.
     * @param serverNum
     *      The number of servers that the <code>DownloadJobs</code> will use
     *      to run.
     * @param endTime
     *      The end time for the timestep where the simulation will stop
     *      running.
     * @param dlSpeed
     *      The speed of the <code>DownloadJobs</code>. This is also known as
     *      the number of megabytes each job will decrease by every timestep.
     */
    public DownloadScheduler(double premiumProb, double regularProb, int
            serverNum, int endTime, int dlSpeed) {
        regularQ = new DownloadQueue();
        premiumQ = new DownloadQueue();

        dlRandomizer = new DownloadRandomizer(premiumProb, regularProb);
        simulationEndTime = endTime;
        currentJobs = new DownloadJob[serverNum];

        downloadSpeed = dlSpeed;
    }

    /**
     * This method runs the simulation of <code>DownloadJobs</code> running
     * on a user set number of servers for a user set duration of time. After
     * the simulation is done running, the collected results will be returned
     * as a string.
     *
     * @return
     *      The resulting data after running the simulation as a string.
     */
    public String simulate() {

        System.out.println(String.format("%10s", "").replace(" ", "-") +
                "Simulation Starting"
                + String.format("%10s", "").replace(" ", "-"));

        String results = "";

        for (int i = 0; i <= simulationEndTime; i++) {
            // Generate timestep
            results += "Timestep " + i + ":\n";
            results += generateData();
            results += String.format("%80s", "").replace("" +
                    " ", "-") + "\n";
            currentTime++;
        }

        results += generateSummary();

        return results;
    }

    /**
     * This method generates the data of the current servers and jobs running
     * each timestep which includes the newly added jobs, the status of the
     * queues, the status of the servers, and how much each
     * <code>DownloadJob</code> has downloaded.
     *
     * @return
     *      A string representation of the newly added jobs, status of the
     *      queues, the status of the servers, and how far each
     *      <code>DownloadJob</code> has progressed in a string representation.
     */
    public String generateData() {
        // Insert around here to decrement dlsize for all downloaditems in
        // array.

        String dataString = "";
        String serverString = "";
        String finishedJobs = "";

        int newReg = dlRandomizer.getRegular();
        int newPrem = dlRandomizer.getPremium();

        // Add new download jobs if they are valid
        if (newReg != -1) {
            DownloadJob reg = new DownloadJob(newReg, currentTime,
                    false, currentId);
            regularQ.enqueue(reg);
            newRegJob = "Job#" + reg.getId() + ": Size: " + reg
                    .getDownloadSize() + "mb\n";
            currentId++;
        } else {
            newRegJob = "n/a\n";
        }

        if (newPrem != -1) {
            DownloadJob prem = new DownloadJob(newPrem,
                    currentTime, true, currentId);
            premiumQ.enqueue(prem);
            newPremJob = "Job#" + prem.getId() + ": Size: " + prem
                    .getDownloadSize() + "mb\n";
            currentId++;
        } else {
            newPremJob = "n/a\n";
        }

        // Add the new data to the return string
        dataString += "\tNew Regular Job: " + newRegJob;
        dataString += "\tNew Premium Job: " + newPremJob;

        // Iterate through the array and find empty cells.
        for (int i = 0; i < currentJobs.length; i++) {

            // Decrement download sizes
            if (currentJobs[i] != null) {
                // Check if the job is not done
                if (currentJobs[i].getDownloadSizeRemaining()  - downloadSpeed > 0)
                    currentJobs[i].setDownloadSizeRemaining(currentJobs[i]
                        .getDownloadSizeRemaining() - downloadSpeed);
                else {
                    // Add the recently finished job (download completed with
                    // 0mb left)to the finishedJob string
                    finishedJobs += String.format("Job %d finished, %s job. " +
                            "%dmb served, " +
                            "Total wait time: %d\n", currentJobs[i].getId(),
                            ((currentJobs[i].isPremium()) ? "Premium" :
                                    "Regular"), currentJobs[i]
                            .getDownloadSize(), currentTime - currentJobs[i]
                            .getTimeRequested());

                    if (currentJobs[i].isPremium()) {
                        premiumJobCount++;
                        premiumData += currentJobs[i].getDownloadSize();
                        premiumTotalWait += currentTime - currentJobs[i]
                                .getTimeRequested();
                    } else {
                        regularJobCount++;
                        regularData += currentJobs[i].getDownloadSize();
                        regularTotalWait += currentTime - currentJobs[i]
                                .getTimeRequested();
                    }

                    currentJobs[i] = null; // Clear job since it is done.
                    // Now we gotta enqueue a new job
                    addJob(i);
                }
            } else {
                addJob(i);
            }

            // Check if null again after adding any new download jobs.
            serverString += "\tServer " + (i + 1) + ": " + ((currentJobs[i] !=
                    null) ?
            currentJobs[i].toString() : "idle\n");

        }

        // Add queue data
        dataString += generatePremiumQueueData();
        dataString += generateRegularQueueData();


        return dataString + serverString + finishedJobs;
    }

    /**
     * Adds a job to the currentJobs array if the queues are not empty and
     * when currentJobs[i] where i is an integer is null.
     *
     * @param i
     *      The parameter that represents the integer value of the server.
     *
     * @throws EmptyQueueException
     *      This exception is thrown if the queues are empty.
     */
    public void addJob(int i) throws EmptyQueueException {
        try {
//        if (!premiumQ.isEmpty()) { // If premiumQ has jobs. These
//            // have priority.
            currentJobs[i] = (DownloadJob) premiumQ.dequeue();
            return;
        } catch (EmptyQueueException e) {
            //System.out.println(e.getMessage());
        }
//        } else if (!regularQ.isEmpty()) {
        try {
            currentJobs[i] = (DownloadJob) regularQ.dequeue();
            return;
        } catch (EmptyQueueException e) {
            //System.out.println(e.getMessage());
        }
        //}

    }

    /**
     * Generates the summary data that considers all of the
     * <code>DownloadJobs</code> that have ran throughout the entire simulation.
     * @return
     */
    public String generateSummary() {

        String simulationSummary = "";

        simulationSummary += concatWithNewLine("Simulation" +
                        " " + "Ended:", false);
        simulationSummary += concatWithNewLine("Total Jobs" +
                " Served: " + Integer.toString(premiumJobCount + regularJobCount),
                true);
        simulationSummary += concatWithNewLine("Total " +
                        "Premium Jobs Served: " + premiumJobCount, true);
        simulationSummary += concatWithNewLine("Total Regular Jobs Served: " +
                        regularJobCount, true);
        simulationSummary += concatWithNewLine("Total Data Served: " +
                        (premiumData + regularData) + "mb", true);
        simulationSummary += concatWithNewLine("Total Premium Data Served: " +
                        premiumData + "mb", true);
        simulationSummary += concatWithNewLine("Total Regular Data Served: " +
                        regularData + "mb", true);

        // Avoids the divide by zero error
        if (premiumJobCount != 0)
            simulationSummary += concatWithNewLine("Average Premium Wait Time: "
                    + (premiumTotalWait / premiumJobCount), true);
        else
            simulationSummary += concatWithNewLine("Average Premium Wait Time: "
                    + "n/a", true);

        // Avoids the divide by zero error
        if (regularJobCount != 0)
            simulationSummary += concatWithNewLine("Average Regular Wait Time: "
                    + (regularTotalWait / regularJobCount), true);
        else
            simulationSummary += concatWithNewLine("Average Regular Wait Time: "
                    + "n/a", true);

        simulationSummary += concatWithNewLine(String.format("%10s", "")
                .replace(" ", "-") + "Thank You For Running the Simulator" +
                String.format("%10s", "").replace(" ", "-"), false);

        return simulationSummary;
    }

    /**
     * Returns a string with the carriage line return character at the end
     * with an option to tab the text if specified.
     *
     * @param message
     *      The string that the method will act on.
     * @param isTabbed
     *      If true, a tab character will be added in the front of the stirng
     *      If false, nothing is done.
     * @return
     *      Returns the formatted version of the string.
     */
    public String concatWithNewLine(String message, boolean
            isTabbed) {
        return ((isTabbed) ? "\t" : "" ) + message + "\n";
    }

    // GUI METHODS

    /**
     * This method runs the simulation of <code>DownloadJobs</code> running
     * on a user set number of servers for a user set duration of time. After
     * the simulation is done running, the collected results will be returned
     * as a string.
     *
     * @return
     *      The resulting data after running the simulation as a string.
     */
    public String simulateGUI() {

        String results = "";

        // Generate timestep
        results += "Timestep " + currentTime + ":\n";
        results += generateData();
        results += String.format("%80s", "").replace("" +
                " ", "-") + "\n";
        currentTime++;

        return results;
    }

    /**
     * Returns the current timestep in seconds of the download simulation.
     *
     * @return
     *      Returns the current timestep which is an integer of the download
     *      simulation.
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * Generates a string representation of all the items in the premium queue.
     *
     * @return
     *      A string with all the <code>DownloadJobs</code> in the premium
     *      queue.
     */
    public String generatePremiumQueueData() {
        String dataString = "";

        // Add queue data
        dataString += "\tPremium Queue: " + premiumQ.toString() + "\n";

        return dataString;
    }

    /**
     * Generates a string representation of all the items in the regular queue.
     *
     * @return
     *      A string with all the <code>DownloadJobs</code> in the regular
     *      queue.
     */
    public String generateRegularQueueData() {
        String dataString = "";

        // Add queue data
        dataString += "\tRegular Queue: " + regularQ.toString() + "\n";

        return dataString;
    }
}
