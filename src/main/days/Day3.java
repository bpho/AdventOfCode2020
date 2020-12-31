package main.days;

import main.models.Day3Tuple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
Take the input and use modulo logic with multi-dim array of '#' and '.'
 */
public class Day3 {

    private static int numOfRows;
    private static int numOfColumns;
    private static final List<String> tobogganMapUnstructured = new ArrayList<>();

    private String[][] constructMap() {
        String[][] localTobogganMap = new String[numOfRows][numOfColumns];
        int row = 0;
        for (String entry : tobogganMapUnstructured) {
            for (int i = 0; i < entry.length(); i++) {
                localTobogganMap[row][i] = Character.toString(entry.charAt(i));
            }
            row++;
        }
        return localTobogganMap;
    }

    private long numOfTreesHit(String[][] tobogganMap, int xMovement, int yMovement) {
        int traveledRow = 0;
        int traveledCol = 0;
        long treesHit = 0;
        while (traveledRow < numOfRows-1) {
            traveledRow = (traveledRow + yMovement);
            traveledCol = (traveledCol + xMovement) % numOfColumns;
//            System.out.println("On Row: " +traveledRow +" and Column: " +traveledCol +" Landmark is: " +tobogganMap[traveledRow][traveledCol]);
            if (tobogganMap[traveledRow][traveledCol].equals("#")) {
//                System.out.println("TREE HIT!");
                treesHit++;
            }
        }
        return treesHit;
    }

    private void buildList(String filePath){
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            reader.lines().forEach(entry -> {
                tobogganMapUnstructured.add(entry);
                numOfColumns = entry.length();
                numOfRows++;
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day3_input.txt";
        Day3 day3 = new Day3();
        day3.buildList(filePath);
        String[][] tobogganMap = day3.constructMap();
//        Common.print2dMatrix(tobogganMap);
        System.out.println("Rows: " +numOfRows +" Columns: " +numOfColumns);

//      Part 1: x = 3, y = 1
        long treesHit = day3.numOfTreesHit(tobogganMap, 3, 1);
        System.out.println("Part 1 -- Num of trees hit: "+treesHit);

//      Part 2: [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)] multiplied
        List<Day3Tuple> listOfTuples = new ArrayList<>();
        listOfTuples.add(new Day3Tuple<>(1, 1));
        listOfTuples.add(new Day3Tuple<>(3, 1));
        listOfTuples.add(new Day3Tuple<>(5, 1));
        listOfTuples.add(new Day3Tuple<>(7, 1));
        listOfTuples.add(new Day3Tuple<>(1, 2));
        long finalCount = 1;
        for (Day3Tuple tuple : listOfTuples) {
            finalCount = finalCount * day3.numOfTreesHit(tobogganMap, (Integer)tuple.x, (Integer)tuple.y);
        }
        System.out.println("Part 2 -- Num of trees hit: "+finalCount);
    }
}
