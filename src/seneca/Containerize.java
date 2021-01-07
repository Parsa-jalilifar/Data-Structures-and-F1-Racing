
package seneca;
import java.util.*;
import java.io.*;

public class Containerize {

    private String line = "";
    private final String cvsSplitBy = ",";
    private String lapDataFile;
    private String pitStopDataFile;
    private static ArrayList<Timer> functionProcessTimes = new ArrayList<Timer>();

    public static ArrayList<Timer> getFunctionProccessTimesContainer(){return functionProcessTimes; }

    Containerize() {
        this.lapDataFile = "";
        this.pitStopDataFile = "";
    }

    Containerize(String lapDataFile,String pitStopDataFile){
        this.lapDataFile = lapDataFile;
        this.pitStopDataFile = pitStopDataFile;
    }

  // getAverageLapTimePerRace //

    public Map<String,Double> averageLapTimePerRace() throws FileNotFoundException {
        Timer timeForOneFunction = new Timer();

        Map<String, Integer> lapsPerRace = new HashMap<String,Integer>();
        Map<String, Double>  timePerRace = new HashMap<String, Double>();

        try {

            BufferedReader inputStream = new BufferedReader(new FileReader(lapDataFile));

            while ((line = inputStream.readLine()) != null) {

                if(line.charAt(0) != 'r') {
                    String[] fields = line.split(cvsSplitBy);

                    if (lapsPerRace.containsKey(fields[0])) lapsPerRace.put(fields[0], lapsPerRace.get(fields[0]) + 1);
                    else lapsPerRace.put(fields[0],0);

                    if (timePerRace.containsKey(fields[0])) timePerRace.put(fields[0], timePerRace.get(fields[0]) + Double.parseDouble(fields[5])/1000);
                    else timePerRace.put(fields[0],Double.parseDouble(fields[5])/1000);
                }
            }

            if (inputStream != null) inputStream.close();

            lapsPerRace.forEach((key, value) -> {
                timePerRace.forEach((k,v) -> { if (k == key) timePerRace.put(k,v/value); });
            });

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        timeForOneFunction.endTimeAddName("Average Lap Time Per Race");
        functionProcessTimes.add(timeForOneFunction);

        return timePerRace;
    }

  // averageLapTimePerRacer

    public Map<String,Double> averageLapTimePerRacer() throws FileNotFoundException {
        Timer timeForOneFunction = new Timer();

        Map<String, Integer> lapsPerRacer = new HashMap<String,Integer>();
        Map<String, Double>  timePerRacer = new HashMap<String, Double>();

        try {

            BufferedReader inputStream = new BufferedReader(new FileReader(lapDataFile));

            while ((line = inputStream.readLine()) != null) {

                if(line.charAt(0) != 'r') {
                    String[] fields = line.split(cvsSplitBy);

                    if (lapsPerRacer.containsKey(fields[1])) lapsPerRacer.put(fields[1], lapsPerRacer.get(fields[1]) + 1);
                    else lapsPerRacer.put(fields[1],0);

                    // will be losing precision here try to fix that
                    if (timePerRacer.containsKey(fields[1])) timePerRacer.put(fields[1], timePerRacer.get(fields[1]) + Double.parseDouble(fields[5])/1000);
                    else timePerRacer.put(fields[1],Double.parseDouble(fields[5])/1000);
                }
            }

            if (inputStream != null) inputStream.close();
            Iterator<Map.Entry<String, Integer> > iterator = lapsPerRacer.entrySet().iterator();
            Iterator<Map.Entry<String, Double> > iteratorTwo = timePerRacer.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                if (entry.getValue().equals(0) || entry.getKey().equals("827")) iterator.remove();
            }

            while (iteratorTwo.hasNext()) {
                Map.Entry<String, Double> entry = iteratorTwo.next();
                if (entry.getValue().equals(0.00) || entry.getKey().equals("827")) iteratorTwo.remove();
            }

            lapsPerRacer.forEach((key, value) -> {
                timePerRacer.forEach((k,v) -> { if (k == key) timePerRacer.put(k,v/ value); });
            });

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        timeForOneFunction.endTimeAddName("Average Lap Time Per Racer");
        functionProcessTimes.add(timeForOneFunction);

        return timePerRacer;
    }

  // averagePitStopTimePerRace

    public Map<String,Double> averagePitStopTimePerRace() throws FileNotFoundException {
        Timer timeForOneFunction = new Timer();

        Map<String, Integer> stopsPerRace = new HashMap<String,Integer>();
        Map<String, Double>  pitTimePerRace = new HashMap<String, Double>();

        try {

            BufferedReader inputStream = new BufferedReader(new FileReader(pitStopDataFile));

            while ((line = inputStream.readLine()) != null) {

                if(line.charAt(0) != 'r') {
                    String[] fields = line.split(cvsSplitBy);

                    if (stopsPerRace.containsKey(fields[0])) stopsPerRace.put(fields[0], stopsPerRace.get(fields[0]) + 1);
                    else stopsPerRace.put(fields[0],0);

                    // will be losing precision here try to fix that
                    if (pitTimePerRace.containsKey(fields[0])) pitTimePerRace.put(fields[0], pitTimePerRace.get(fields[0]) + Double.parseDouble(fields[6])/1000);
                    else pitTimePerRace.put(fields[0],Double.parseDouble(fields[6])/1000);
                }
            }

            if (inputStream != null) inputStream.close();

            Iterator<Map.Entry<String, Integer> > iterator = stopsPerRace.entrySet().iterator();
            Iterator<Map.Entry<String, Double> > iteratorTwo = pitTimePerRace.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                if (entry.getValue().equals(0.00)) iterator.remove();;
            }

            while (iteratorTwo.hasNext()) {
                Map.Entry<String, Double> entry = iteratorTwo.next();
                if (entry.getValue().equals(0.00) || entry.getValue().intValue() > 2500) iteratorTwo.remove();
            }

            stopsPerRace.forEach((key, value) -> {
                pitTimePerRace.forEach((k,v) -> { if (k == key) pitTimePerRace.put(k,v/value); });
            });

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        timeForOneFunction.endTimeAddName("Average Pit Stop Time Per Race");
        functionProcessTimes.add(timeForOneFunction);

        return pitTimePerRace;
    }

    // average pit Stop time per Race

    public Map<String,Double> averagePitStopTimePerRacer() throws FileNotFoundException {
        Timer timeForOneFunction = new Timer();

        Map<String, Integer> stopsPerRacer = new HashMap<String,Integer>();
        Map<String, Double>  pitTimePerRacer = new HashMap<String, Double>();
        ArrayList<String> outlierId = new ArrayList<String>();

        try {

            BufferedReader inputStream = new BufferedReader(new FileReader(pitStopDataFile));

            while ((line = inputStream.readLine()) != null) {

                if(line.charAt(0) != 'r') {
                    String[] fields = line.split(cvsSplitBy);

                    if (stopsPerRacer.containsKey(fields[1])) stopsPerRacer.put(fields[1], stopsPerRacer.get(fields[1]) + 1);
                    else stopsPerRacer.put(fields[1],0);

                    // will be losing precision here try to fix that
                    if (pitTimePerRacer.containsKey(fields[1])) pitTimePerRacer.put(fields[1], pitTimePerRacer.get(fields[1]) + Double.parseDouble(fields[6])/1000);
                    else pitTimePerRacer.put(fields[1],Double.parseDouble(fields[6])/1000);
                }
            }

            if (inputStream != null) inputStream.close();

            Iterator<Map.Entry<String, Integer> > iterator = stopsPerRacer.entrySet().iterator();
            Iterator<Map.Entry<String, Double> > iteratorTwo = pitTimePerRacer.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                if (entry.getValue().intValue() < 15) {
                    outlierId.add(entry.getKey());
                    iterator.remove();
                };
            }

            while (iteratorTwo.hasNext()) {
                Map.Entry<String, Double> entry = iteratorTwo.next();
                for (String id: outlierId) {
                    if (entry.getValue().equals(0.00) || entry.getKey() == id) iteratorTwo.remove();
                }
            }

            stopsPerRacer.forEach((key, value) -> {
                pitTimePerRacer.forEach((k,v) -> { if (k == key) pitTimePerRacer.put(k,v/value); });
            });

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        timeForOneFunction.endTimeAddName("Average Pit Stop Time Per Racer");
        functionProcessTimes.add(timeForOneFunction);

        return pitTimePerRacer;
    }

    /************** GetBestRace(RacerID) *****************/

    public Map.Entry<String, Double>  getBestRace(String RacerID) {
        Timer timeForOneFunction = new Timer();

        Map<String, Integer> lapsForRace = new HashMap<String,Integer>();
        Map<String, Double>  timeForRace = new HashMap<String, Double>();
        Map.Entry<String, Double> bestRace = null;

        try {

            BufferedReader inputStream = new BufferedReader(new FileReader(lapDataFile));

            while ((line = inputStream.readLine()) != null) {

                if(line.charAt(0) != 'r') {

                    String[] fields = line.split(cvsSplitBy);

                    if (lapsForRace.containsKey(fields[0])) lapsForRace.put(fields[0], lapsForRace.get(fields[0]) + 1);
                    else {
                        if (fields[1].equals(RacerID)) lapsForRace.put(fields[0],0);
                    }

                    if (timeForRace.containsKey(fields[0])) timeForRace.put(fields[0], timeForRace.get(fields[0]) + Double.parseDouble(fields[5])/1000);
                    else {
                        if (fields[1].equals(RacerID)) timeForRace.put(fields[0], Double.parseDouble(fields[5])/1000);
                    }
                }
            }

            if (inputStream != null) inputStream.close();

            lapsForRace.forEach((key, value) -> {
                timeForRace.forEach((k,v) -> { if (k == key) timeForRace.put(k,v/value); });
            });

            for (Map.Entry<String, Double> entry : timeForRace.entrySet()) {
                if (bestRace == null || entry.getValue().compareTo(bestRace.getValue()) < 0) {
                    bestRace = entry;
                }
            }

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        timeForOneFunction.endTimeAddName("Get Best Race");
        functionProcessTimes.add(timeForOneFunction);

        return bestRace;
    }

}
