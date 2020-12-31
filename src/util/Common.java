package util;

import java.util.Arrays;

public class Common {

    public static void print2dMatrix(String[][] mat) {
        System.out.println("---- Printing 2D Matrix ----");
        for (String[] row : mat) {
            System.out.println(Arrays.toString(row));
        }
    }

}
