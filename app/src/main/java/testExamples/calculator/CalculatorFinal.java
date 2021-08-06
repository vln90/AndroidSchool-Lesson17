package testExamples.calculator;

import testExamples.memory.MemoryFinal;

public class CalculatorFinal {

    private final MemoryFinal mCalculator;

    public CalculatorFinal(MemoryFinal calculatorWithMemory) {
        mCalculator = calculatorWithMemory;
    }

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
        mCalculator.addToValue(number);
    }

    private int getNumberFromMemory() {
        return mCalculator.getCurrentValue();
    }
}