package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Common {

    public static void print2dMatrix(String[][] mat) {
        System.out.println("---- Printing 2D Matrix ----");
        for (String[] row : mat) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static List<String> buildList(String filePath) {
        List<String> inputLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            inputLines = reader.lines().collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return inputLines;
    }

}
