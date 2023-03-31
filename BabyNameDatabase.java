import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the list of BabyNames as well as reading and writing to the files
 * with the BabyNames.
 * @author Justin Lee
 */
public class BabyNameDatabase {

    private String databaseFileName;
    private ArrayList<BabyName> records = new ArrayList<BabyName>();
    private File fileRecord;
    private final int YEAR;

    public BabyNameDatabase(String databaseFileName) {
        this.databaseFileName = databaseFileName;
        Scanner yearFind = new Scanner(databaseFileName);
        yearFind.useDelimiter("[^0-9]+");
        YEAR = yearFind.nextInt();
    }

    public ArrayList<BabyName> getRecords() {
        return records;
    }

    /**
     * Reads the csv file that holds the baby name birth data and updates
     * the records variable.
     * @param filename name of the file to read from
     * @throws IOException could not find or close file
     */
    public void readRecordsFromBirthDataFile(String filename) throws IOException {
        Scanner in = new Scanner(new File(filename));

        //int count = 0;
        while (in.hasNextLine()) {
        //while (count < 4) {
            String line = in.nextLine();
            if (!isFormattedLine(line)) {
                continue;
            }

            //System.out.println(line);
            processLineFromBirthDataFile(line, YEAR);
            //count++;
        }
    }

    public boolean isFormattedLine(String currentLine) {
        Scanner line = new Scanner(currentLine);
        line.useDelimiter(",");
        return line.hasNextInt();
    }

    /**
     * Processes one formatted line of the csv file into baby names and
     * adds/updates the records array.
     * @param line the string holding the line from the csv file
     * @param year when the data is from
     */
    public void processLineFromBirthDataFile(String line, int year){
        GenderOfName[] genders = {GenderOfName.MALE, GenderOfName.FEMALE};

        Scanner currentLine = new Scanner(line);
        currentLine.useDelimiter(",");
        currentLine.next(); // First column (ranking)

        for (int i = 0; i < 2; i++) {
            String name = currentLine.next(); // Second / Fourth column (name)

            int births; // Third / Fifth column (# of births)
            if (currentLine.hasNextInt()) {
                births = currentLine.nextInt();
            }
            else {
                String tempNumber = currentLine.next().substring(1);
                String secondHalf = currentLine.next();
                tempNumber += secondHalf.substring(0,secondHalf.length()-1);
                births = Integer.parseInt(tempNumber);
            }
            //System.out.println("Name: "+name+", Births: "+births);

            BabyName baby = new BabyName(name, genders[i]);
            addToRecord(baby, births);
        }
    }

    public void addToRecord(BabyName baby, int births) {
        boolean duplicateName = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getName().equals(baby.getName())) {
                records.get(i).addYearData(births, YEAR);
                records.get(i).setGender(GenderOfName.NEUTRAL);
                duplicateName = true;
                break;
            }
        }

        if (!duplicateName) {
            baby.addData(births, YEAR);
            records.add(baby);
        }
    }
}

