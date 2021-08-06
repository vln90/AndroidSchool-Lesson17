package testExamples.calculator;

import testExamples.memory.MemoryStatic;

public class CalculatorStatic {

    public int addition(int number) {
        addNumberToMemory(number);
        return getNumberFromMemory();
    }

    public int tripleAddition(int number) {
        addition(number);
        addition(number);
        return addition(number);
    }

    private void addNumberToMemory(int number) {
        MemoryStatic.addToValue(number);
    }

    private int getNumberFromMemory() {
        return MemoryStatic.getCurrentValue();
    }
}