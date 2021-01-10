package main.models;

/*
Used in Day 8 puzzle to represent a line of instruction
 */
public class ConsoleInstruction {

    private int index;
    private String instructionKey;
    private int instructionValue;

    public ConsoleInstruction(int index, String instructionKey, int instructionValue) {
        this.index = index;
        this.instructionKey = instructionKey;
        this.instructionValue = instructionValue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getInstructionKey() {
        return instructionKey;
    }

    public void setInstructionKey(String instructionKey) {
        this.instructionKey = instructionKey;
    }

    public int getInstructionValue() {
        return instructionValue;
    }

    public void setInstructionValue(int instructionValue) {
        this.instructionValue = instructionValue;
    }

}
