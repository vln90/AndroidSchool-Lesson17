package testExamples.calculator;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import testExamples.memory.MemoryFinal;

/**
 * Тестирование {@link CalculatorFinal}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MemoryFinal.class)
public class CalculatorFinalTest {

    private static final int ARGUMENT = 10;
    private static final int ADDITION_RESULT = 110;
    private static final int ADDITION_RESULT_TRIPLE = 130;

    private MemoryFinal mMemoryFinal;

    private CalculatorFinal mCalculatorFinal;

    @Before
    public void setUp() {
        mMemoryFinal = PowerMockito.mock(MemoryFinal.class);
        mCalculatorFinal = new CalculatorFinal(mMemoryFinal);
    }

    @Test
    public void testAddition() {

        // Arrange
        Mockito.when(mMemoryFinal.getCurrentValue()).thenReturn(ADDITION_RESULT);

        int expectedResult = ADDITION_RESULT;

        // Act
        int actualResult = mCalculatorFinal.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        Mockito.verify(mMemoryFinal, Mockito.times(1)).addToValue(ARGUMENT);
    }

    @Test
    public void testTripleAddition() {

        // Arrange
        Mockito.when(mMemoryFinal.getCurrentValue()).thenReturn(ADDITION_RESULT_TRIPLE);

        int expectedResult = ADDITION_RESULT_TRIPLE;

        // Act
        int actualResult = mCalculatorFinal.tripleAddition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        Mockito.verify(mMemoryFinal, Mockito.times(3)).addToValue(ARGUMENT);
    }
}