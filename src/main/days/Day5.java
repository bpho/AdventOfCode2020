package main.days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 Read input file into ArrayList of seats
 Using aspects of Binary Search Algo
 */
public class Day5 {

    private static List<String> boardingPasses;
    private static final List<Integer> seatIds = new ArrayList<>();

    private void buildList(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            boardingPasses = reader.lines().collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int determineHighestSeatId(List<String> boardingPasses) {
        int highestSeatValue = 0;
        for (String pass : boardingPasses) {
            int row = determineRowAndCol(pass.substring(0, 7), 0, 127, 'F', 'B');
            int col = determineRowAndCol(pass.substring(7), 0, 7, 'L', 'R');
            int seatIdValue = row * 8 + col;
            if (seatIdValue > highestSeatValue) {
                highestSeatValue = seatIdValue;
            }
            seatIds.add(seatIdValue);
        }
        return highestSeatValue;
    }

    private int determineRowAndCol(String seatCombo, int min, int max, char minChar, char maxChar) {
        for (int i = 0; i < seatCombo.length(); i++) {
            int mid = (min + max) / 2;
            char currentChar = seatCombo.charAt(i);
//          Last letter determines min or max, don't change values
            if (i == seatCombo.length() - 1) {
                if (currentChar == minChar) {
                    return min;
                } else if (currentChar == maxChar) {
                    return max;
                }
            } else {
//              Division rounds down so add 1 for upper half min value
                if (currentChar == minChar) {
                    max = mid;
                } else if (currentChar == maxChar) {
                    min = mid + 1;
                }
            }
        }
        System.err.println("Unable to return rol or col");
        return 0;
    }

//  Part 2
    private int determinePassengerSeatId(List<Integer> seatIds) {
        seatIds.stream().sorted().forEach(System.out::println);
        Collections.sort(seatIds);
        int previousSeatId = 0;
        for (Integer seatId : seatIds) {
            if (seatId - previousSeatId == 2) {
                return seatId - 1;
            }
            previousSeatId = seatId;
        }
        System.err.println("Unable to find seatId");
        return 0;
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day5_input.txt";
        Day5 day5 = new Day5();
        day5.buildList(filePath);
        int highestSeatId = day5.determineHighestSeatId(boardingPasses);
        System.out.println("Part 1 - Highest Seat ID is: " +highestSeatId);

        int passengerSeatId = day5.determinePassengerSeatId(seatIds);
        System.out.println("Part 2 - My Seat ID is: " +passengerSeatId);
    }
}
