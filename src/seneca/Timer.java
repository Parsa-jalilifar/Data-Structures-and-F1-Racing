package seneca;

public class Timer {

    /* These members will contain values that are meaningless unless their difference is taken  */
    private long startTime;
    private long endTime;
    /* Will be calculated with the timeDifference method*/
    private long timeElapsed;
    /* Will be used to hold the name of the function */
    private String nameOfFunction;

    @Override
    public String toString() {
        return nameOfFunction + " function took " + timeElapsed + " Micro Seconds to Perform\n";
    }

    /* Initializes this class*/
    public Timer(){
        startTimer();
        this.endTime = (long) 0.0;
    }

    /*Return function Name*/
    public String getFunctionName() { return nameOfFunction; };

    /* Timer starts counting */
    public void startTimer(){ startTime = System.nanoTime(); }

    /* Ends the timer */
    public void endTimer(){
        endTime = System.nanoTime();
    }

    /* Gets the difference in time */
    public void timeDifference(){
       long temp = (long) ((getEndTime() - getStartTime()) + 0.000000001); //without the extra 1 nano second, it will not work
       timeElapsed = temp / 1000; // convert to microseconds
    }

    /* Gets the startTime */
    public long getStartTime() {
        return startTime;
    }

    /* Gets the EndTime */
    public long getEndTime() {
        return endTime;
    }

    /* Gets the timeElapsed, make sure to call the timeDifference() method before this
    Default is microSeconds */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /* Gets the timeElapsed in Milliseconds */
    public long getTimeElapsedMilliSeconds() {
        return (timeElapsed / 1000000);
    }

    /* Gets the timeElapsed in Seconds */
    public long getTimeElapsedSeconds() {
        return (timeElapsed / 1000000000);
    }

    /* Gets the name of the function */
    public String getNameOfFunction() {
        return nameOfFunction;
    }
    /* Sets the name of the function */
    public void setNameOfFunction(String nameOfFunction) {
        this.nameOfFunction = nameOfFunction;
    }

    /* Ends the timer, takes the difference, and sets the nane of the function */
    public void endTimeAddName(String name){
       this.endTimer();
       this.timeDifference();
       this.setNameOfFunction(name);
    }
}

