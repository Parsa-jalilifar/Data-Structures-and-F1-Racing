package seneca;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class RacingInformation {

    private String driverDataFile;
    private String trackDataFile;
    private String line = "";
    private final String cvsSplitBy = ",";

    RacingInformation() {
        this.driverDataFile = "";
        this.trackDataFile = "";
    }

    RacingInformation(String driverDataFile, String trackDataFile) {
        this.driverDataFile = driverDataFile;
        this.trackDataFile = trackDataFile;
    }

    public String[] getDriverInformation(String ID) {

        String[] driverInfo = null;

        try {

            BufferedReader inputStream = new BufferedReader(new FileReader(driverDataFile));

            while ((line = inputStream.readLine()) != null) {

                if (line.charAt(0) != 'r') {
                    String[] fields = line.split(cvsSplitBy);

                    if(fields[0].equals(ID)) {
                        driverInfo = new String[9];
                        for (int i = 0; i < fields.length; i++) {
                            driverInfo[i] = fields[i];
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return driverInfo;
    }

    public String[] getRaceInformation(String ID) {

        String[] raceInfo = null;

        try {

            BufferedReader inputStream = new BufferedReader(new FileReader(trackDataFile));

            while ((line = inputStream.readLine()) != null) {

                if (line.charAt(0) != 'r') {
                    String[] fields = line.split(cvsSplitBy);

                    if(fields[0].equals(ID)) {
                        raceInfo = new String[9];
                        for (int i = 0; i < fields.length; i++) {
                            raceInfo[i] = fields[i];
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return raceInfo;
    }

}
