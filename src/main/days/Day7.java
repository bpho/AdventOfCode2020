package main.days;

import util.Common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/*
Create HashMap of bag types, and for each bag type have a list
of other bag colors that it's able to hold

Part 1 - Don't worry about the number of each bag type it can hold
Focus on the list of bags after the "contain" keyword
 */
public class Day7 {

    public static void main(String[] args) {
        String filePath = "src/resources/day6_input.txt";
        Day7 day7 = new Day7();
        List<String> inputLines = Common.buildList(filePath);
    }

}
