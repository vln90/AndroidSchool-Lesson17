package testExamples.memory;

public class MemoryStatic {

    private static int mValue = 0;

    public static void addToValue(int newValue) {
        mValue += newValue;
    }

    public static void clearValue() {
        mValue = 0;
    }

    public static int getCurrentValue() {
        return 0;
    }

    public static void setNewValue(int memory) {
        mValue = memory;
    }
}
