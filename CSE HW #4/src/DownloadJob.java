/**
 * The <code>DownloadJob</code> class implemented as objects by the
 * <code>DownloadQueue</code> which stores information regarding the download
 * size, remaining download size, time when the download started, whether if
 * it is premium or regular, and its ID. The properties within this object
 * can be manipulated by getters and setters.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 *
 */
public class DownloadJob {

    // Instance Fields
    private int downloadSize;
    private int downloadSizeRemaining;
    private int timeRequested;
    private boolean isPremium;
    private int id = 1; // Default value

    /**
     * Constructor for the <code>DownloadJob</code> which takes in the download
     * size, the remaining download size, the time that the download started,
     * its premium status, and the ID and gets returned.
     *
     * @param dlSize
     *      The download size of the download job in MB.
     * @param timeReq
     *      The timestep when the <code>DownloadJob</code> is started.
     * @param isPremium
     *      Specifies if this <code>DownloadJob</code> is premium or not.
     * @param id
     *      Sets the ID of this <code>DownloadJob</code>.
     */
    public DownloadJob(int dlSize, int timeReq, boolean
            isPremium, int id) {
        downloadSize = dlSize;
        downloadSizeRemaining  = dlSize;
        timeRequested = timeReq;
        this.isPremium = isPremium;
        this.id = id;
    }

    /**
     * Returns the download size.
     *
     * @return
     *      Returns the file size of the <code>DownloadJob</code>.
     */
    public int getDownloadSize() {
        return downloadSize;
    }

    /**
     * Sets the download size of this <code>DownloadJob</code>.
     *
     * @param downloadSize
     *      The randomly generated file size of the download in MB.
     */
    public void setDownloadSize(int downloadSize) {
        this.downloadSize = downloadSize;
    }

    /**
     * Returns the remaining file size left to download.
     *
     * @return
     *      The remaining amount of memory to download in MB.
     */
    public int getDownloadSizeRemaining() {
        return downloadSizeRemaining;
    }

    /**
     * Updates the remaining file size left to download for this
     * <code>DownloadJob</code>.
     *
     * @param downloadSizeRemaining
     *      Remaining file size left to download. If <code>downloadSizeRemaining</code> is
     *      less than 0, 0 is returned.
     */
    public void setDownloadSizeRemaining(int downloadSizeRemaining) {
        if (downloadSizeRemaining > 0)
            this.downloadSizeRemaining = downloadSizeRemaining;
        else
            this.downloadSizeRemaining = 0;
    }

    /**
     * Returns the timestep of when this download was initiated.
     *
     * @return
     *      The timestep of when this <code>DownloadJob</code> was started.
     */
    public int getTimeRequested() {
        return timeRequested;
    }

    /**
     * Sets the timestep of when this download was initated.
     *
     * @param timeRequested
     *      The timestep of when the download was started.
     */
    public void setTimeRequested(int timeRequested) {
        this.timeRequested = timeRequested;
    }

    /**
     * Returns a boolean that determines if it is premium or not.
     *
     * @return
     *      Returns true if it is premium, returns false if it is regular.
     */
    public boolean isPremium() {
        return isPremium;
    }

    /**
     * Sets the premium status of this <code>DownloadJob</code>.
     *
     * @param premium
     *      A boolean that shows if the download is premium or not. True if
     *      it is premium and false if it is regular.
     */
    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    /**
     * Returns the ID associated with this <code>DownloadJob</code>.
     *
     * @return
     *      An integer representation of the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of this <code>DownloadJob</code>.
     *
     * @param id
     *      An integer that represents this object's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns a string representation of this <code>DownloadJob</code>
     * including its ID, download size, download size remaining, and its
     * premium status.
     *
     * @return
     *      A string representation of this <code>DownloadJob</code>
     *      including its ID, download size, download size remaining, and its
     *      premium status.
     */
    public String toString() {
        return String.format("[#%d: %smb total, %smb remaining, Request Time:" +
                        " %d," +
                " %s]", id, downloadSize, downloadSizeRemaining,
                timeRequested, (isPremium) ? "Premium" : "Regular") + "\n";
    }
}
