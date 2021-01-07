package seneca;

import java.util.*;

public class Analyze {

    /************** Holds functions execution Duration ***************/
    private static ArrayList<Timer> functionProcessTimes = new ArrayList<Timer>();

    public static ArrayList<Timer> getFunctionProccessTimesAnalyze(){return functionProcessTimes; }

    /************** DetermineshortestRace **************/

    public static Map.Entry<String, Double> getShortestRace(Map<String, Double>  timePerRace) {
        Timer timeForOneFunction = new Timer();
        Map.Entry<String, Double> shortestRace = null;

        for (Map.Entry<String, Double> entry : timePerRace.entrySet()) {
            if (shortestRace == null || entry.getValue().compareTo(shortestRace.getValue()) < 0) shortestRace = entry;
        }

        timeForOneFunction.endTimeAddName("Get Shortest Race");
        functionProcessTimes.add(timeForOneFunction);
        return shortestRace;
    }

    /************* DeterminelongestRace ****************/

    public static Map.Entry<String, Double> getLongestRace(Map<String, Double>  timePerRace) {
        Timer timeForOneFunction = new Timer();
        Map.Entry<String, Double> worstTrack = null;

        for (Map.Entry<String, Double> entry : timePerRace.entrySet()) {
            if (worstTrack == null || entry.getValue().compareTo(worstTrack.getValue()) > 0) worstTrack = entry;
        }

        timeForOneFunction.endTimeAddName("Get Longest Race");
        functionProcessTimes.add(timeForOneFunction);
        return worstTrack;
    }

    /******** DetermineMeanLapTimeforRacers ************/

    public static Double getMeanLapTimeForRacers(Map<String, Double>  timePerRacer) {
        Timer timeForOneFunction = new Timer();
        Double totalLapTime = 0.00;
        for(Map.Entry<String, Double> entry: timePerRacer.entrySet()){
            totalLapTime += entry.getValue();
        }

        timeForOneFunction.endTimeAddName("Mean Lap Time For Racers");
        functionProcessTimes.add(timeForOneFunction);
        return (totalLapTime/timePerRacer.size());

    }

    /******** DetermineMeanLapTimeforRaces *************/

    public static Double getMeanLapTimeForRaces(Map<String, Double>  timePerRace) {
        Timer timeForOneFunction = new Timer();
        Double totalLapTimeRace = 0.00;
        for (Map.Entry<String, Double> entry : timePerRace.entrySet()) {
            totalLapTimeRace += entry.getValue();
        }

        timeForOneFunction.endTimeAddName("Mean Lap Time For Races");
        functionProcessTimes.add(timeForOneFunction);
        return (totalLapTimeRace / timePerRace.size());
    }

    /*************** DetermineMeanPitStopRacers *********/

    public static Double getMeanPitStopRacers(Map<String, Double>  pitTimePerRacer) {
        Timer timeForOneFunction = new Timer();
        Double totalPitTime = 0.00;
        for (Map.Entry<String, Double> entry : pitTimePerRacer.entrySet()) {
            totalPitTime += entry.getValue();
        }

        timeForOneFunction.endTimeAddName("Mean Pit Stops For Racers");
        functionProcessTimes.add(timeForOneFunction);
        return (totalPitTime / pitTimePerRacer.size());
    }

    /*************** DetermineMeanPitStopRaces ***********/

    public static Double getMeanPitStopRaces(Map<String, Double>  pitTimePerRace) {
        Timer timeForOneFunction = new Timer();
        Double totalPitTimeRaces = 0.00;
        for (Map.Entry<String, Double> entry : pitTimePerRace.entrySet()) {
            totalPitTimeRaces += entry.getValue();
        }

        timeForOneFunction.endTimeAddName("Mean Pit Stops For Races");
        functionProcessTimes.add(timeForOneFunction);
        return (totalPitTimeRaces / pitTimePerRace.size());
    }

}
