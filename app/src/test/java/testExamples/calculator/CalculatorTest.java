package testExamples.calculator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import testExamples.memory.Memory;

import static org.junit.Assert.assertEquals;

/**
 * Тестирование {@link Calculator}
 */
public class CalculatorTest {

    private static final int START_MEMORY = 100;
    private static final int ARGUMENT = 10;
    private static final int ADDITION_RESULT = 110;
    private static final int ADDITION_RESULT_TRIPLE = 130;

    private static Memory mMemory;

    private Calculator mCalculator;

    @BeforeClass
    public static void beforeClass() {
        mMemory = new Memory();
    }

    @Before
    public void setUp() {
        mMemory.setNewValue(START_MEMORY);
        mCalculator = new Calculator(mMemory);
    }

    @Test
    public void testAddition() {

        // Arrange
        int expectedResult = ADDITION_RESULT;

        // Act
        int actualResult = mCalculator.addition(ARGUMENT);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testTripleAddition() {

        // Arrange
        int expectedResult = ADDITION_RESULT_TRIPLE;

        // Act
        int actualResult = mCalculator.tripleAddition(ARGUMENT);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @After
    public void after() {
        mMemory.clearValue();
    }

    @AfterClass
    public static void afterClass(){
        mMemory = null;
    }
}