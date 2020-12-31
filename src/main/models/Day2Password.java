package main.models;

public class Day2Password {

    private int minNumOfInstances;
    private int maxNumOfInstances;
    private char letterOfInterest;
    private String passwordGiven;

    public Day2Password(String instances, String letter, String password) {
        String[] instanceSplit = instances.split("-");
        this.minNumOfInstances = Integer.valueOf(instanceSplit[0]);
        this.maxNumOfInstances = Integer.valueOf(instanceSplit[1]);
        this.letterOfInterest = letter.charAt(0);
        this.passwordGiven = password;
    }

    public int getMinNumOfInstances() {
        return minNumOfInstances;
    }

    public void setMinNumOfInstances(int minNumOfInstances) {
        this.minNumOfInstances = minNumOfInstances;
    }

    public int getMaxNumOfInstances() {
        return maxNumOfInstances;
    }

    public void setMaxNumOfInstances(int maxNumOfInstances) {
        this.maxNumOfInstances = maxNumOfInstances;
    }

    public char getLetterOfInterest() {
        return letterOfInterest;
    }

    public void setLetterOfInterest(char letterOfInterest) {
        this.letterOfInterest = letterOfInterest;
    }

    public String getPasswordGiven() {
        return passwordGiven;
    }

    public void setPasswordGiven(String passwordGiven) {
        this.passwordGiven = passwordGiven;
    }
}