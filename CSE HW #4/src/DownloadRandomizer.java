
public class DownloadRandomizer {
    private int minFileSize=114;
    private int maxFileSize=219;
    private double probPremium;
    private double probRegular;
    /**
     * Initialize the probability of downloads here.
     * @param probPremium must be between 0 and 1.
     * @param probRegular must be between 0 and 1.
     */
    public DownloadRandomizer(double probPremium, double probRegular) {
        super();
        this.probPremium = probPremium;
        this.probRegular = probRegular;
    }
    private boolean didPremiumAppear(){
        if(Math.random()<probPremium)
            return true;
        return false;
    }
    private boolean didRegularAppear(){
        if(Math.random()<probRegular)
            return true;
        return false;
    }
    private int getSize(){
        return (int)(Math.random()*(maxFileSize+1-minFileSize)+minFileSize);
    }
    /**This generates new Regular Jobs.
     * This has to be called once a timestep. Will return the filesize
     * of the new download job if applicable (or -1 if no new job appeared).
     * @return the size of the job to be added in this time step (or -1 if no new job should be added)
     */
    public int getRegular(){
        if(!didRegularAppear())
            return -1;
        else return getSize();
    }
    /**This generates new Premium Jobs.
     * This has to be called once a timestep. Will return the filesize
     * of the new download job if applicable (or -1 if no new job appeared).
     * @return the size of the job to be added in this time step (or -1 if no new job should be added)
     */
    public int getPremium(){
        if(!didPremiumAppear())
            return -1;
        else return getSize();
    }

}