package main.days;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

    private static final String[] requiredFields = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
    private static final List<String> yearFields = Arrays.asList("byr", "iyr", "eyr");
    private static final List<String> validEyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    private static final Map<String, String> yearRules = new HashMap<>();
    private static List<String> inputLines = new ArrayList<>();
    private static List<String> organizedEntries = new ArrayList<>();
    private static List<String> validPassports = new ArrayList<>();
    private final int REQUIRED_VALID_FIELDS = 7;

    private void buildList(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            inputLines = reader.lines().collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void groupItems(List<String> inputLines) {
        String workingEntry = "";
        for (int i = 0; i < inputLines.size(); i++) {
            String entry = inputLines.get(i);
            workingEntry = workingEntry.concat(entry).concat(" ");
            if (entry.equals("") || i == inputLines.size() - 1) {
                organizedEntries.add(workingEntry);
                workingEntry = "";
            }
        }
    }

    private int getNumOfValidIds(List<String> organizedEntries) {
        int validIds = 0;
        for (String entry : organizedEntries) {
            if (Stream.of(requiredFields).allMatch(entry::contains)) {
                validPassports.add(entry);
                validIds++;
            }
        }
        return validIds;
    }

    /*
    Part 2 logic
     */
    private int getNumOfValidIdsWithCheck(List<String> validPassports) {
        int validIdsAfterCheck = 0;
        for (String entry : validPassports) {
            String[] sections = entry.split(" ");
            if (allChecksPass(sections)) {
                validIdsAfterCheck++;
            }
        }
        return validIdsAfterCheck;
    }

//  TODO: Potential to refactor if-else logic to separate functions
    private boolean allChecksPass(String[] sections) {
        int numOfValidFields = 0;
        for (String section : sections) {
            String[] sectionSplit = section.split(":");
            String passportField = sectionSplit[0];
            String passportValue = sectionSplit[1];

            if (yearFields.contains(passportField)) {
                if (passportValue.length() == 4) {
                    int year = Integer.valueOf(passportValue);
                    String[] yearRange = yearRules.get(passportField).split("-");
                    if (year >= Integer.valueOf(yearRange[0]) && year <= Integer.valueOf(yearRange[1])) {
                        numOfValidFields++;
                    }
                }
            } else if (passportField.equals("hgt")) {
                String unit = passportValue.substring(passportValue.length()-2);
                int measurement;
                try {
                    measurement = Integer.valueOf(passportValue.substring(0, passportValue.length() - 2));
                } catch (NumberFormatException nfe) {
                    System.err.println("Invalid measurement given: "+passportValue);
                    continue;
                }
//                System.out.println("Height Measurement: " +measurement +" Height unit: " +unit);
                if (unit.equals("cm")) {
                    if (measurement >= 150 && measurement <= 193) {
                        numOfValidFields++;
                    }
                } else if (unit.equals("in")) {
                    if (measurement >= 59 && measurement <= 76) {
                        numOfValidFields++;
                    }
                }
            } else if (passportField.equals("hcl")) {
//                a # followed by exactly six characters 0-9 or a-f. USE REGEX HERE
                if (passportValue.matches("^#([a-f0-9]{6})$")) {
                    numOfValidFields++;
                }
            } else if (passportField.equals("ecl")) {
                // if (validEyeColors.stream().anyMatch(eyeColor -> passportValue.equals(eyeColor))) {
                if (validEyeColors.stream().anyMatch(passportValue::equals)) {
                    numOfValidFields++;
                }
            } else if (passportField.equals("pid")) {
                if (passportValue.length() == 9) {
                    try {
                        int intValue = Integer.parseInt(passportValue);
                        numOfValidFields++;
                    } catch (NumberFormatException nfe) {
                        System.err.println("Unparsable passport value to int: " +passportValue);
                        continue;
                    }
                }
            }
        }

        return (numOfValidFields == REQUIRED_VALID_FIELDS) ? true : false;
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day4_input.txt";
        Day4 day4 = new Day4();
        day4.buildList(filePath);
        day4.groupItems(inputLines);
        int numOfValidIds = day4.getNumOfValidIds(organizedEntries);
        System.out.println("Part 1 - Total num of valid ids: "+numOfValidIds);

        yearRules.put("byr", "1920-2002");
        yearRules.put("iyr", "2010-2020");
        yearRules.put("eyr", "2020-2030");
        int numOfValidIdsWithCheck = day4.getNumOfValidIdsWithCheck(validPassports);
        System.out.println("Part 2 - Total num of valid ids: "+numOfValidIdsWithCheck);
    }
}
