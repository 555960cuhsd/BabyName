import java.util.ArrayList;

/**
 * Object that represents a name for a baby. Includes the sex of the name
 * and birth data for the number of babies born with that name in a
 * particular year.
 * @author Justin Lee
 */
public class BabyName {

    private String name;
    private GenderOfName gender;
    private ArrayList<Integer> birthCounts = new ArrayList<Integer>();
    private ArrayList<Integer> years = new ArrayList<Integer>();

    public BabyName(String name, GenderOfName gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public GenderOfName getGender() {
        return gender;
    }

    public ArrayList<Integer> getBirthCounts() {
        return birthCounts;
    }

    public ArrayList<Integer> getYears() {
        return years;
    }

    public void setGender(GenderOfName gender) {
        this.gender = gender;
    }

    public void addData(int births, int year) {
        if (!years.isEmpty()) {
            int index = 0;
            while (year > years.get(index)) {
                index++;
                if (index >= years.size()) {
                    break;
                }
            }
            years.add(index, year);
            birthCounts.add(index, births);
        }
        else {
            years.add(year);
            birthCounts.add(births);
        }
    }

    public void addData(ArrayList<Integer> births, ArrayList<Integer> years) {
        for (int i = 0; i < years.size(); i++) {
            addData(births.get(i), years.get(i));
        }
    }

    public void addYearData(int addedBirths, int year) {
        int index = years.indexOf(year);
        birthCounts.set(index, birthCounts.get(index) + addedBirths);
    }

    /**
     * Formats the object as a String.
     * @return formatted String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Name: " + name + "\nSex of Name: " + gender.toString().toLowerCase());
        for (int i = 0; i < years.size(); i++){
            if (i == 0){
                result.append("\nData: ");
            }
            else{
                result.append(String.format("(%d, %d) "), birthCounts.get(i), years.get(i));
            }
            if (i == years.size()-1){
                result.deleteCharAt(result.length()-1); // Remove extra space
            }
        }
        return result.toString();
    }
}