package testExamples.memory;

public final class MemoryFinal {

    private int mValue = 0;

    public void addToValue(int newValue) {
        mValue += newValue;
    }

    public void clearValue() {
        mValue = 0;
    }

    public int getCurrentValue() {
        return mValue;
    }

    public void setNewValue(int memory) {
        mValue = memory;
    }
}
