package testExamples.calculator;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import testExamples.memory.Memory;

import static org.mockito.Mockito.*;

/**
 * Тестирование {@link Calculator}
 */
public class CalculatorSpyTest {

    private static final int START_MEMORY = 100;
    private static final int ARGUMENT = 10;
    private static final int ADDITION_RESULT = 110;

    private Calculator mCalculator;

    private Memory mMemory;

    // @Spy
    // private Memory mMemory = new Memory();

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        mMemory = spy(new Memory());
        mCalculator = new Calculator(mMemory);
    }

    @Test
    public void testAdditionNormal() {

        // Arrange
        mMemory.setNewValue(START_MEMORY);
        int expectedResult = ADDITION_RESULT;

        // Act
        int actualResult = mCalculator.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        verify(mMemory, times(1)).addToValue(ARGUMENT);
    }

    @Test
    public void testAdditionWithSpy() {

        // Arrange
        when(mMemory.getCurrentValue()).thenReturn(ADDITION_RESULT);

        int expectedResult = ADDITION_RESULT;

        // Act
        int actualResult = mCalculator.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        verify(mMemory, times(1)).addToValue(ARGUMENT);
    }

    @Test
    public void testAdditionDoNothing() {

        // Arrange
        mMemory.setNewValue(START_MEMORY);
        doNothing().when(mMemory).addToValue(ARGUMENT);

        int expectedResult = START_MEMORY;

        // Act
        int actualResult = mCalculator.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        verify(mMemory, times(1)).addToValue(ARGUMENT);
    }
}