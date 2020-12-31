package main.days;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
1. Store list of numbers in ArrayList
2. Iterate through list taking each number and subtracting it from 2020
3. Then check if result exists in ArrayList of numbers
 */
public class Day1 {

    private static final List<Integer> expenseEntries = new ArrayList<>();
    private static final int YEAR = 2020;

    private int retrieveProductPart1(List<Integer> expenseEntries){
        for (Integer entry : expenseEntries) {
            int entryValue = entry.intValue();
            int diff = YEAR - entryValue;
            if (expenseEntries.contains(Integer.valueOf(diff))){
                int product = entryValue * diff;
                return product;
            }
        }
        System.out.println("Product not found");
        return 0;
    }

//  Faster to sort so we can start with the smaller numbers
    private int retrieveProductPart2(List<Integer> expenseEntries){
        Collections.sort(expenseEntries);
        int product = 0;
        for (int i = 0; i < expenseEntries.size(); i++) {
            for (int j = i+1; j < expenseEntries.size(); j++) {
                int first = expenseEntries.get(i).intValue();
                int second = expenseEntries.get(j).intValue();
                int sum = first + second;
                if (sum >= YEAR) {
                    continue;
                } else {
                    int diff = YEAR - sum;
                    if (expenseEntries.contains(Integer.valueOf(diff))){
                        product = first * second * diff;
                        return product;
                    } else {
                        continue;
                    }
                }
            }
        }
        return product;
    }

    private void buildList(String filePath){
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            reader.lines().forEach(entry -> {
                expenseEntries.add(Integer.valueOf(entry));
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day1_input.txt";
        Day1 day1 = new Day1();
        day1.buildList(filePath);
        int finalProductPart1 = day1.retrieveProductPart1(expenseEntries);
        int finalProductPart2 = day1.retrieveProductPart2(expenseEntries);
        System.out.println("Part 1 - Final Product is: "+finalProductPart1);
        System.out.println("Part 2 - Final Product is: "+finalProductPart2);
    }
}
