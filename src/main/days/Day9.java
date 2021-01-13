package main.days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

    private List<Long> buildListNumbers(String filePath) {
        List<Long> inputLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines().forEach(line -> inputLines.add(Long.parseLong(line)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return inputLines;
    }

    /*
    Part 1:
    Iterate through list until encounter integer that does not comply with rule of being
    the sum of at least one pair of numbers from the previous 25

    1. Two Arguments: List of preamble of 25 integers and the 26th number
    2. Take the 26th number and subtract the number at index i from it,
    see if the difference exists amongst the 24 other numbers
     */
    private long findIncorrectTrailingNumber(List<Long> inputLines) {
        int currentIndex = 25;
        long valueToTest = 0;
        boolean validNumber  = true;
        while (validNumber) {
            List<Long> preambleSet = inputLines.subList(currentIndex-25, currentIndex);
            valueToTest = inputLines.get(currentIndex);
            validNumber = isNumberValid(preambleSet, valueToTest);
            currentIndex++;
        }
        return valueToTest;
    }

    private boolean isNumberValid(List<Long> preambleSet, long currentValue) {
        for (int i = 0; i < preambleSet.size(); i++) {
            long diff = currentValue - preambleSet.get(i);
            if (preambleSet.contains(diff)) {
                if (preambleSet.indexOf(diff) != i) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    Part 2:
    Given the incorrect number found from part 1, find list of contiguous numbers anywhere
    in the list that add up to the incorrect number. Encryption weakness is the sum of the
    SMALLEST and LARGEST number from this contiguous list.

    1. Starting at the index = 0
    2. If the working number is greater than the invalid number, skip to next
    3. If it's less than, add the one next to it, if new sum is greater than exit inner loop and start at next index
     */
    private long getEncryptionWeakness(List<Long> inputLines, long invalidTrailingNum) {
        int startingIndex;
        int currentIndex;
        long sumOfContiguousValues = 0;
        for (int i = 0; i < inputLines.indexOf(invalidTrailingNum); i++) {
            for (int j = i; j < inputLines.indexOf(invalidTrailingNum); j++) {
                startingIndex = i;
                currentIndex = j;
                sumOfContiguousValues = sumOfContiguousValues + inputLines.get(j);
                if (sumOfContiguousValues > invalidTrailingNum) {
                    sumOfContiguousValues = 0;
                    break;
                } else if (sumOfContiguousValues == invalidTrailingNum) {
                    return findSumOfSmallestAndLargest(inputLines, startingIndex, currentIndex);
                }
            }
        }
        System.err.print("Unable to find encryption weakness");
        return 0;
    }

    private long findSumOfSmallestAndLargest(List<Long> inputLines, int start, int end) {
        long smallestValue = inputLines.get(start);
        long largestValue = inputLines.get(start);
        List<Long> contiguousList = inputLines.subList(start, end+1);
        for (long currentListValue : contiguousList) {
            if (currentListValue < smallestValue) {
                smallestValue = currentListValue;
            } else if (currentListValue > largestValue) {
                largestValue = currentListValue;
            }
        }
        return smallestValue + largestValue;
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day9_input.txt";
        Day9 day9 = new Day9();
        List<Long> inputLines = day9.buildListNumbers(filePath);
        long invalidTrailingNum = day9.findIncorrectTrailingNumber(inputLines);
        System.out.println("Part 1 - First invalid number is: " +invalidTrailingNum);

        long encryptionWeakness = day9.getEncryptionWeakness(inputLines, invalidTrailingNum);
        System.out.println("Part 2 - Encryption weakness value is: " +encryptionWeakness);
    }
}
