package main.days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 {

    private static List<String> inputLines = new ArrayList<>();
    private static final List<String> groupAnswersPart1 = new ArrayList<>();
    private static final List<List<String>> groupAnswersPart2 = new ArrayList<>();

    private void buildList(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            inputLines = reader.lines().collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void organizeToGroups(List<String> inputLines) {
        String currentGroupPart1 = "";
        List<String> currentGroupPart2 = new ArrayList<>();
        for (int i = 0; i < inputLines.size(); i++) {
            String currentLine = inputLines.get(i);
            // Empty line denotes new group and also EOF (at size() - 1)
            if (currentLine.equals("")) {
                groupAnswersPart1.add(currentGroupPart1);
                currentGroupPart1 = "";
                groupAnswersPart2.add(currentGroupPart2);
                currentGroupPart2 = new ArrayList<>();
            } else if (i == inputLines.size() - 1) {
                currentGroupPart1 = currentGroupPart1.concat(currentLine);
                groupAnswersPart1.add(currentGroupPart1);
                currentGroupPart2.add(currentLine);
                groupAnswersPart2.add(currentGroupPart2);
            } else {
                currentGroupPart1 = currentGroupPart1.concat(currentLine);
                currentGroupPart2.add(currentLine);
            }
        }
    }

    /*
    Convert string to stream of characters, then create a Hashset.
    Hashset doesn't allow duplicates so count # of characters in set
     */
    private int determineNumUniqueYesPart1(List<String> groupAnswers) {
        int sumOfCount = 0;
        for (String groupAnswer : groupAnswers) {
            System.out.println("The group answer is: "+groupAnswer);
            Set<Character> uniqueChar = groupAnswer
                    .chars()
                    .mapToObj(c -> (char) c).collect(Collectors.toSet());
            int uniqueYesCount = uniqueChar.size();
            sumOfCount += uniqueYesCount;
            System.out.println("Set values: " +uniqueChar);
            System.out.println("Num of set values: " +sumOfCount);
        }
        return sumOfCount;
    }

    private int determineNumUniqueYesPart2(List<List<String>> totalGroupAnswers) {
        int totalCompleteYes = 0;
        for (List<String> groupAnswer : totalGroupAnswers) {
            int sizeOfGroup = groupAnswer.size();
            int totalCompleteYesFromGroup = numOfCompleteYes(groupAnswer, sizeOfGroup);
            totalCompleteYes += totalCompleteYesFromGroup;
        }
        return totalCompleteYes;
    }

    private int numOfCompleteYes(List<String> groupAnswers, int numOfPeople) {
        Map<Character, Integer> numOfYesPerQuestion = new HashMap<>();
        int completeYesCount = 0;
        for (String individualAnswers: groupAnswers) {
            for (int i = 0; i < individualAnswers.length(); i++) {
                char currentChar = individualAnswers.charAt(i);
                if (!numOfYesPerQuestion.containsKey(currentChar)) {
                    numOfYesPerQuestion.put(currentChar, 1);
                } else {
                    numOfYesPerQuestion.put(currentChar, numOfYesPerQuestion.get(currentChar) + 1);
                }
            }
        }

        // Iterate through Map of values for each character, if count matches the total
        // number of people in group, then treat as complete as everyone answered yes
        for (Map.Entry<Character, Integer> entry : numOfYesPerQuestion.entrySet()) {
            if (entry.getValue() == numOfPeople) {
                completeYesCount++;
            }
        }

        return completeYesCount;
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day6_input.txt";
        Day6 day6 = new Day6();
        day6.buildList(filePath);
        day6.organizeToGroups(inputLines);
        int numOfTotalYesPart1 = day6.determineNumUniqueYesPart1(groupAnswersPart1);
        System.out.println("Part 1 - Sum of total Yes: " +numOfTotalYesPart1);

        int numOfTotalYesPart2 = day6.determineNumUniqueYesPart2(groupAnswersPart2);
        System.out.println("Part 2 - Sum of total complete Yes: " +numOfTotalYesPart2);
    }
}