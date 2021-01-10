package main.days;

import util.Common;

import java.util.*;

public class Day7 {

    /*
    Part 1:
    Recursively check bags that are able to hold "shiny gold" -- storing result in a Set
    to avoid duplicate bags
    Example: If dark blue bag (can hold) -> faded green -> shiny gold AND dim lavender -> faded green,
    do not count faded green twice
     */
    private Set<String> determineBagsThatCanHold(List<String> inputLines, String bagColor) {
        Set<String> totalBagColors = new HashSet<>();
        List<String> recursiveBags = new ArrayList<>();
        for (String line : inputLines) {
            String[] stringSplit = line.split("contain");
            if (stringSplit[1].contains(bagColor)) {
                recursiveBags.add(line);
                System.out.println("Contents for " +stringSplit[0] +": " +stringSplit[1]);
                totalBagColors.add(stringSplit[0]);
            }
        }
        if (!recursiveBags.isEmpty()) {
            System.out.println("----- NEXT SET -----");
            for (String line : recursiveBags) {
                // Gather new list of bags that can hold shiny gold, construct new bagColor string to check
                String[] bagColorRecursiveSplit = line.split("contain")[0].split(" ");
                String bagColorRecursive = String.join(" ", bagColorRecursiveSplit[0], bagColorRecursiveSplit[1]);
                totalBagColors.addAll(determineBagsThatCanHold(inputLines, bagColorRecursive));
            }
        }
        return totalBagColors;
    }

    /*
    Part 2:
    Recursively check each bag that shiny gold can hold
     */
    private int determineBagsContainedWithin(List<String> inputLines, String bagColor, int numOfBags) {
        int totalNumOfBags = 0;
        for (String line : inputLines) {
            String[] stringSplit = line.split("contain");
            if (stringSplit[0].contains(bagColor)) {
                String[] listOfContainingBags = Arrays.stream(stringSplit[1].split(","))
                        .map(String::trim)
                        .toArray(String[]::new);
                for (String containingBag : listOfContainingBags) {
                    if (containingBag.contains("no other bags")) {
                        // Return 0 if there are no other bags (will happen within a subsequent call)
                        return totalNumOfBags;
                    } else {
                        String[] bagText = containingBag.split(" ");
                        int subNumOfBags = Integer.parseInt(bagText[0]);
                        String subBagColor = String.join(" ", bagText[1], bagText[2]);
                        // Add num of bags from current set from total, then recursively add the num of bags
                        // from each subsequent set of bag totals
                        totalNumOfBags += subNumOfBags;
                        totalNumOfBags += determineBagsContainedWithin(inputLines, subBagColor, subNumOfBags);
                    }
                }
            }
        }
        totalNumOfBags = numOfBags * totalNumOfBags;
        return totalNumOfBags;
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day7_input.txt";
        Day7 day7 = new Day7();
        List<String> inputLines = Common.buildList(filePath);
        String bagColor = "shiny gold";
        int numOfBagColors = day7.determineBagsThatCanHold(inputLines, bagColor).size();
        System.out.println("Part 1 - Number of bags that can hold " +bagColor +": "+numOfBagColors);
        int totalNumOfBags = day7.determineBagsContainedWithin(inputLines, bagColor, 1);
        System.out.println("Part 2 - Number of bags that shiny gold can hold: " +totalNumOfBags);
    }

}
