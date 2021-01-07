package seneca;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    // function for removing duplication in arrayList
    public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {

        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (int i = 0; i < list.size(); i++)
            if (!newList.contains(list.get(i)))
                newList.add(list.get(i));

        return newList;
    }

    public static void main(String[] args) throws IOException {


        /*** Edit all relevant Files to remove data related to *******/
        /*** years older than 2000, Required for specifications ******/
        /*************************************************************/

        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        // Storing ids of races and drivers in ArrayLists
        ArrayList<Integer> listRaceIdF1 = new ArrayList<Integer>();
        ArrayList<Integer> listDriverIdF2 = new ArrayList<Integer>();

        try {

            // Defining place of original file and new file

            inputStream = new BufferedReader(new FileReader("database/races.csv"));
            outputStream = new PrintWriter(new FileWriter("newDatabase/newRaces.csv"));

            String dataF1 = "";

            while ((dataF1 = inputStream.readLine()) != null) {

                String tempYear = "";
                String tempIdF1 = "";
                String temp = "";

                // skipping first line while program is reading from file

                if (dataF1.charAt(0) != 'r') {

                    tempIdF1 = dataF1.substring(0, dataF1.indexOf(','));
                    temp = dataF1.substring(dataF1.indexOf(',') + 1);
                    tempYear = temp.substring(0, temp.indexOf(","));
                }

                // checking date of races to be happend after 1999 and storing data about those
                // races in a new file

                if (tempYear != "" && Integer.parseInt(tempYear) > 1999) {

                    outputStream.println(dataF1);
                    listRaceIdF1.add(Integer.parseInt(tempIdF1));
                }
            }
        } finally {
            if (inputStream != null)
                inputStream.close();

            if (outputStream != null)
                outputStream.close();
        }

        /**Editing lapTimes.csv file**/

        try {

            inputStream = new BufferedReader(new FileReader("database/lapTimes.csv"));
            outputStream = new PrintWriter(new FileWriter("newDatabase/newLapTimes.csv"));

            String data = "";

            while ((data = inputStream.readLine()) != null) {

                String temp = "";
                String tempIdF2 = "";
                String tempDeriverId = "";

                if (data.charAt(0) != 'r') {

                    tempIdF2 = data.substring(0, data.indexOf(','));
                    temp = data.substring(data.indexOf(',') + 1);
                    tempDeriverId = temp.substring(0, temp.indexOf(","));

                }

                // extra driver ids and checking if two files have same race ids then store
                // data in a new file

                if (tempIdF2 != "")
                    for (Integer num : listRaceIdF1)
                        if (num == Integer.parseInt(tempIdF2)) {
                            listDriverIdF2.add(Integer.parseInt(tempDeriverId));
                            outputStream.println(data);
                        }
            }
        } finally {
            if (inputStream != null)
                inputStream.close();

            if (outputStream != null)
                outputStream.close();
        }

        // removing duplication for drivers ids

        listDriverIdF2 = removeDuplicates(listDriverIdF2);

        /** Editing races.csv file **/

        try {

            inputStream = new BufferedReader(new FileReader("database/pitStops.csv"));
            outputStream = new PrintWriter(new FileWriter("newDatabase/newpitStops.csv"));

            String data = "";

            while ((data = inputStream.readLine()) != null) {

                String temp = "";
                String tempDeriverIdF3 = "";

                if (data.charAt(0) != 'r') {

                    temp = data.substring(data.indexOf(',') + 1);
                    tempDeriverIdF3 = temp.substring(0, temp.indexOf(","));

                }

                // Checking matches of driver ids of lapTimes with pitStops if they are match
                // then copy data in a new file

                if (tempDeriverIdF3 != "")
                    for (Integer num : listDriverIdF2)
                        if (num == Integer.parseInt(tempDeriverIdF3)) {
                            outputStream.println(data);
                        }
            }
        } finally {
            if (inputStream != null)
                inputStream.close();

            if (outputStream != null)
                outputStream.close();
        }

        /** Editing drivers.csv file **/

        try {

            inputStream = new BufferedReader(new FileReader("database/drivers.csv"));
            outputStream = new PrintWriter(new FileWriter("newDatabase/newdrivers.csv"));

            String dataF4 = "";

            while ((dataF4 = inputStream.readLine()) != null) {

                String tempDeriverIdF4 = "";

                // skiping first line while porgram is reading from file

                if (dataF4.charAt(0) != 'd') {

                    tempDeriverIdF4 = dataF4.substring(0, dataF4.indexOf(','));

                }

                // checking to have drivers with same id just like other files

                if (tempDeriverIdF4 != "")
                    for (Integer num : listDriverIdF2)
                        if (num == Integer.parseInt(tempDeriverIdF4)) {
                            outputStream.println(dataF4);
                        }
            }
        } finally {
            if (inputStream != null)
                inputStream.close();

            if (outputStream != null)
                outputStream.close();
        }

        /*******************File Modification Ended*********************/
        /*************************************************************/


        /*******************Starting Data Analysis********************/
        /*************************************************************/

       Containerize containerize = new Containerize("newDatabase/newLapTimes.csv", "newDatabase/newpitStops.csv");

        Map<String,Double> averageLapTimePerRace = new HashMap<>(containerize.averageLapTimePerRace());
        Map<String,Double> averageLapTimePerRacer = new HashMap<>(containerize.averageLapTimePerRacer());

        Map<String,Double> averagePitStopPerRace = new HashMap<>(containerize.averagePitStopTimePerRace());
        Map<String,Double> averagePitStopPerRacer = new HashMap<>(containerize.averagePitStopTimePerRacer());

        Map.Entry<String, Double> racersBestRaceOne = containerize.getBestRace("30");

        Map.Entry<String, Double> shortestRace = Analyze.getShortestRace(averageLapTimePerRace);
        Map.Entry<String, Double> longestRace = Analyze.getLongestRace(averageLapTimePerRace);

        Double meanLapTimeForRacers = Analyze.getMeanLapTimeForRacers(averageLapTimePerRacer);
        Double meanLapTimeForRaces = Analyze.getMeanLapTimeForRaces(averageLapTimePerRace);
        Double meanPitTimeForRacers = Analyze.getMeanPitStopRacers(averagePitStopPerRacer);
        Double meanPitTimeForRaces = Analyze.getMeanPitStopRaces(averagePitStopPerRace);


        ArrayList<Timer> functionProcessTimes = new ArrayList<Timer>();
        ArrayList<Timer> allTimesAnalyzeClass = Analyze.getFunctionProccessTimesAnalyze();
        ArrayList<Timer> allTimesContainerizeClass = containerize.getFunctionProccessTimesContainer();

        /******************Printing Out Values************************/
        /*************************************************************/

        System.out.println("\nSpecialized Results of Data Analysis");
        System.out.println("------------------------------------\n");

        RacingInformation infoRetriever = new RacingInformation("newDatabase/newdrivers.csv", "newDatabase/newRaces.csv");
        DecimalFormat df = new DecimalFormat("##.##");

        // Best Race
        String[] bestRaceForRacerOne = infoRetriever.getRaceInformation(racersBestRaceOne.getKey());
        String[] Racer = infoRetriever.getDriverInformation("30");
        System.out.println(Racer[4] + " " + Racer[5] + " Best Race Was The " + bestRaceForRacerOne[1] + " " + bestRaceForRacerOne[4]);

        // Shortest Race
        String[] shortestRaceInfo = infoRetriever.getRaceInformation(shortestRace.getKey());
        System.out.println("\nThe Shortest Race Was The " + shortestRaceInfo[1] + " " + shortestRaceInfo[4]);

        // Longest Race
        String[] longestRaceInfo = infoRetriever.getRaceInformation(longestRace.getKey());
        System.out.println("\nThe Longest Race Was The " + longestRaceInfo[1] + " " + longestRaceInfo[4] + "\n");

        System.out.println("\nResults of Data Analysis across all Races");
        System.out.println("-----------------------------------------\n");

        // mean Lap Time for Racers
        System.out.println("The mean lap Time for Racers is " + df.format(meanLapTimeForRacers) + " seconds \n");

        // mean Lap Time for Races
        System.out.println("The mean lap Time for Races is " + df.format(meanLapTimeForRaces) + " seconds \n");

        // mean Pitstop Time for Racers
        System.out.println("The mean Pit Stop Time for Racers is " + df.format(meanPitTimeForRacers) + " seconds \n");

        // mean Pitstop Time for Races
        System.out.println("The mean Pit Stop Time for Races is " + df.format(meanPitTimeForRaces) + " seconds \n");

        /* Prints out the Timing results for all functions in the Anaylze Class */
        System.out.println("\n Execution Duration of each method From The Analyze Class ");
        System.out.println(" --------------------------------------------------------\n");
        for(int i = 0; i < allTimesAnalyzeClass.size(); i++){
            System.out.println(" " + allTimesAnalyzeClass.get(i).toString());
        }

        /* Prints out the Timing results for all functions in the Anaylze Class */
        System.out.println("\n Execution Duration of each method From The Containerize Class");
        System.out.println(" -------------------------------------------------------------\n");
        for(int i = 0; i < allTimesContainerizeClass.size(); i++){
            System.out.println(" " + allTimesContainerizeClass.get(i).toString());
        }
    }
}
