package main.days;

import main.models.ConsoleInstruction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day8 {

    private static final List<ConsoleInstruction> listOfInstructions = new ArrayList<>();
    private static final List<ConsoleInstruction> listOfJmpAndNopsPart1 = new ArrayList<>();

    private void buildList(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines().forEach(entry -> {
                String[] sections = entry.split(" ");
                ConsoleInstruction instruction = new ConsoleInstruction(listOfInstructions.size(), sections[0], Integer.parseInt(sections[1]));
                listOfInstructions.add(instruction);
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Part 1:
    Start with first instruction, follow each instruction and add to list of already executed
    instruction until reach an instruction that has already been executed.
    Track the accumulator value in the meantime until that repeated instruction is met.
     */
    private int determineAccValuePart1(List<ConsoleInstruction> listOfInstructions) {
        int accValue = 0;
        ConsoleInstruction currentInstruction = listOfInstructions.get(0);
        List<ConsoleInstruction> executedInstructions = new ArrayList<>();
        while (!executedInstructions.contains(currentInstruction)) {
//            System.out.println("Part 1 - Current Instruction: " +currentInstruction.getInstructionKey() +" " +currentInstruction.getInstructionValue() +" at index: " +currentInstruction.getIndex());
            executedInstructions.add(currentInstruction);
            int currentIndex = currentInstruction.getIndex();
            String instructionKey = currentInstruction.getInstructionKey();
            int instructionValue = currentInstruction.getInstructionValue();
            switch (instructionKey) {
                case "acc":
                    accValue += currentInstruction.getInstructionValue();
                    currentInstruction = listOfInstructions.get(currentIndex + 1);
                    break;
                case "nop":
                    listOfJmpAndNopsPart1.add(currentInstruction);
                    currentInstruction = listOfInstructions.get(currentIndex + 1);
                    break;
                case "jmp":
                    listOfJmpAndNopsPart1.add(currentInstruction);
                    currentInstruction = listOfInstructions.get(currentIndex + instructionValue);
                    break;
            }
        }
//        System.out.println("Part 1 - Loop reached! Repeated instruction: " +currentInstruction.getInstructionKey() +" " +currentInstruction.getInstructionValue() +" at index: " +currentInstruction.getIndex());
        return accValue;
    }

    /*
    Part 2:
    Re-using the function from part 1, but with the new list of instructions passed in with the single
    line of execution changed from either jmp to nop or vice versa. If this new list of instructions still
    results in an infinite loop, return 0 (meaning no accValue captured) -- otherwise if the next index
    is the end of the execution list, then return the FINAL accValue.

    Hint: It has to be one of the "jmp(s)" or "nop(s)" from the instructions before the infinite loop
    Capture all the jmps and nops from Part 1 -- test the instructions by changing each one until it completes
     */
    private int determineAccValuePart2(List<ConsoleInstruction> listOfInstructions) {
        int accValue = 0;
        boolean endingReached = false;
        ConsoleInstruction currentInstruction = listOfInstructions.get(0);
        List<ConsoleInstruction> executedInstructions = new ArrayList<>();
        while (!executedInstructions.contains(currentInstruction)) {
            executedInstructions.add(currentInstruction);
            int currentIndex = currentInstruction.getIndex();
            String instructionKey = currentInstruction.getInstructionKey();
            int instructionValue = currentInstruction.getInstructionValue();
            if (currentIndex + 1 == listOfInstructions.size()) {
                endingReached = true;
            }
            switch (instructionKey) {
                case "acc":
                    accValue += currentInstruction.getInstructionValue();
                    if (!endingReached) {
                        currentInstruction = listOfInstructions.get(currentIndex + 1);
                        break;
                    } else {
                        return accValue;
                    }
                case "nop":
                    if (!endingReached) {
                        currentInstruction = listOfInstructions.get(currentIndex + 1);
                        break;
                    } else {
                        return accValue;
                    }
                case "jmp":
                    if (!endingReached) {
                        currentInstruction = listOfInstructions.get(currentIndex + instructionValue);
                        break;
                    } else {
                        return accValue;
                    }
            }

        }
        return 0;
    }

    /*
    Runs through the list of instructions with the changed line.
    Take turns changing THE SINGLE IDENTIFIED "jmp" and "nop" instruction to their opposites until a valid
    accValue (of not 0) is returned from function above -- meaning end of execution was reachable.
     */
    private int testJmpNopChanges(List<ConsoleInstruction> listOfJmpAndNopsPart1) {
        List<ConsoleInstruction> newList = new ArrayList<>(listOfInstructions);
        int accValue = 0;
        for (ConsoleInstruction instruction : listOfJmpAndNopsPart1) {
            String instructionKey = instruction.getInstructionKey();
            int instructionIndex = instruction.getIndex();
            int instructionValue = instruction.getInstructionValue();
            // Create the new instruction to replace in the original list
            ConsoleInstruction newInstruction = new ConsoleInstruction(instructionIndex, instructionKey, instructionValue);
            if (instructionKey.equals("jmp")) {
                newInstruction.setInstructionKey("nop");
            } else if (instructionKey.equals("nop")) {
                newInstruction.setInstructionKey("jmp");
            }
            newList.set(instruction.getIndex(), newInstruction);
            accValue = determineAccValuePart2(newList);
            // If accValue is no longer 0, return value, otherwise discard the instruction changed
            // and attempt with next one in listOfJmpAndNopsPart1
            if (accValue != 0) {
                return accValue;
            } else {
                newList = new ArrayList<>(listOfInstructions);
            }
        }
        System.err.println("Part 2 - Solution not found");
        return 0;
    }

    public static void main(String[] args) {
        String filePath = "src/resources/day8_input.txt";
        Day8 day8 = new Day8();
        day8.buildList(filePath);
        System.out.println("Number of total instructions: " +listOfInstructions.size());
        int accValuePart1 = day8.determineAccValuePart1(listOfInstructions);
        System.out.println("Part 1 - The acc value before infinite loop is: " +accValuePart1);

        System.out.println("Size of Jmp & Nop list: " +listOfJmpAndNopsPart1.size());
        int accValuePart2 = day8.testJmpNopChanges(listOfJmpAndNopsPart1);
        System.out.println("Part 2 - The acc value after fixing infinite loop is: " +accValuePart2);
    }

}
