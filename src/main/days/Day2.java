package main.days;

import main.models.Day2Password;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {

    private static List<Day2Password> listOfPasswords = new ArrayList<>();

    private int getInstanceCount(char letterOfInterest, String password) {
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == letterOfInterest) {
                count++;
            }
        }
        return count;
    }

    private boolean isCompliant(int count, int min, int max) {
        return (count >= min && count <= max) ? true : false;
    }

    private boolean isCharacterPresentOnce(String password, char letterOfInterest, int posOne, int posTwo) {
        if (password.charAt(posOne-1) == letterOfInterest ^ password.charAt(posTwo-1) == letterOfInterest) {
            return true;
        } else {
            return false;
        }
    }

    private int processPasswordsPart1(List<Day2Password> passwords) {
        int numOfCompliantPasswords = 0;
        for (Day2Password passwordObj : passwords) {
            char letterOfInterest = passwordObj.getLetterOfInterest();
            String password = passwordObj.getPasswordGiven();
            int minNumOfInstances = passwordObj.getMinNumOfInstances();
            int maxNumOfInstances = passwordObj.getMaxNumOfInstances();

            int instanceCount = getInstanceCount(letterOfInterest, password);
            boolean compliant = isCompliant(instanceCount, minNumOfInstances, maxNumOfInstances);

            if (compliant) {
                numOfCompliantPasswords++;
            }
        }
        return numOfCompliantPasswords;
    }

    private int processPasswordsPart2(List<Day2Password> passwords) {
        int numOfCompliantPasswords = 0;
        for (Day2Password passwordObj : passwords) {
            char letterOfInterest = passwordObj.getLetterOfInterest();
            String password = passwordObj.getPasswordGiven();
            int firstPosition = passwordObj.getMinNumOfInstances();
            int secondPosition = passwordObj.getMaxNumOfInstances();

            boolean compliant = isCharacterPresentOnce(password, letterOfInterest, firstPosition, secondPosition);

            if (compliant) {
                numOfCompliantPasswords++;
            }
        }
        return numOfCompliantPasswords;
    }

    private void buildList(String filePath){
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            reader.lines().forEach(entry -> {
                String[] passwordEntry = entry.split(" ");
                Day2Password passwordRules = new Day2Password(passwordEntry[0], passwordEntry[1], passwordEntry[2]);
                listOfPasswords.add(passwordRules);
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day2_input.txt";
        Day2 day2 = new Day2();
        day2.buildList(filePath);
        int compliantPasswords1 = day2.processPasswordsPart1(listOfPasswords);
        System.out.println("Part 1 -- Number of compliant passwords: "+compliantPasswords1);

        int compliantPasswords2 = day2.processPasswordsPart2(listOfPasswords);
        System.out.println("Part 2 -- Number of compliant passwords: "+compliantPasswords2);
    }
}
