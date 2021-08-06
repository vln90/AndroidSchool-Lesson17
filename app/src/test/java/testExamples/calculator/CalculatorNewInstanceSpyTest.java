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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестирование {@link CalculatorNewInstance}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CalculatorNewInstance.class, Memory.class})
public class CalculatorNewInstanceSpyTest {

    private static final int START_MEMORY = 100;
    private static final int ARGUMENT = 10;
    private static final int ADDITION_RESULT = 110;

    private Memory mMemory;

    private CalculatorNewInstance mCalculatorNewInstance;

    @Before
    public void setUp() throws Exception {
        mMemory = Mockito.spy(new Memory());
        PowerMockito.whenNew(Memory.class).withNoArguments().thenReturn(mMemory);

        mCalculatorNewInstance = new CalculatorNewInstance();
    }

    @Test
    public void testAdditionNormal() {

        // Arrange
        // Mockito.when(mCalculatorWithMemory.getMemory()).thenReturn(ADDITION_RESULT);

        mMemory.setNewValue(START_MEMORY);

        int expectedResult = ADDITION_RESULT;

        // Act
        int actualResult = mCalculatorNewInstance.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        Mockito.verify(mMemory, Mockito.times(1)).addToValue(ARGUMENT);
    }

    @Test
    public void testAdditionDoNothing() {

        // Arrange
        mMemory.setNewValue(START_MEMORY);
        doNothing().when(mMemory).addToValue(ARGUMENT);

        int expectedResult = START_MEMORY;

        // Act
        int actualResult = mCalculatorNewInstance.addition(ARGUMENT);

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
        int actualResult = mCalculatorNewInstance.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        verify(mMemory, times(1)).addToValue(ARGUMENT);
    }
}