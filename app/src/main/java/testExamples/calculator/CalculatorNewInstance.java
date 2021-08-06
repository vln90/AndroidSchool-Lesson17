package testExamples.calculator;

import testExamples.memory.Memory;

public class CalculatorNewInstance {

    private final Memory mCalculator;

    public CalculatorNewInstance() {
        mCalculator = new Memory();
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