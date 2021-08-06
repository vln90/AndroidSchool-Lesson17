package testExamples.calculator;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import testExamples.memory.Memory;

/**
 * Тестирование {@link CalculatorNewInstance}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CalculatorNewInstance.class, Memory.class})
public class CalculatorNewInstanceTest {

    private static final int ARGUMENT = 10;
    private static final int ADDITION_RESULT = 110;
    private static final int ADDITION_RESULT_TRIPLE = 130;

    private Memory mMemory;

    private CalculatorNewInstance mCalculatorNewInstance;

    @Before
    public void setUp() throws Exception {
        mMemory = PowerMockito.mock(Memory.class);
        PowerMockito.whenNew(Memory.class).withNoArguments().thenReturn(mMemory);

        mCalculatorNewInstance = new CalculatorNewInstance();
    }

    @Test
    public void testAddition() {

        // Arrange
        Mockito.when(mMemory.getCurrentValue()).thenReturn(ADDITION_RESULT);

        int expectedResult = ADDITION_RESULT;

        // Act
        int actualResult = mCalculatorNewInstance.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        Mockito.verify(mMemory, Mockito.times(1)).addToValue(ARGUMENT);
    }

    @Test
    public void testTripleAddition() {

        // Arrange
        Mockito.when(mMemory.getCurrentValue()).thenReturn(ADDITION_RESULT_TRIPLE);

        int expectedResult = ADDITION_RESULT_TRIPLE;

        // Act
        int actualResult = mCalculatorNewInstance.tripleAddition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        Mockito.verify(mMemory, Mockito.times(3)).addToValue(ARGUMENT);
    }
}