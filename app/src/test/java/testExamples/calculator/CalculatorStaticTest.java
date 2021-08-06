package testExamples.calculator;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import testExamples.memory.MemoryStatic;

/**
 * Тестирование {@link CalculatorStatic}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MemoryStatic.class)
public class CalculatorStaticTest {

    private static final int ARGUMENT = 10;
    private static final int ADDITION_RESULT = 110;
    private static final int ADDITION_RESULT_TRIPLE = 130;

    private CalculatorStatic mCalculatorStatic;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(MemoryStatic.class);
        mCalculatorStatic = new CalculatorStatic();
    }

    @Test
    public void testAddition() throws Exception {

        // Arrange
        PowerMockito.doNothing().when(MemoryStatic.class, "addToValue", ARGUMENT);
        PowerMockito.when(MemoryStatic.getCurrentValue()).thenReturn(ADDITION_RESULT);

        int expectedResult = ADDITION_RESULT;

        // Act
        int actualResult = mCalculatorStatic.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);

        PowerMockito.verifyStatic(MemoryStatic.class);
        MemoryStatic.addToValue(ARGUMENT);

        PowerMockito.verifyStatic(MemoryStatic.class);
        MemoryStatic.getCurrentValue();
    }

    @Test
    public void testAdditionTwoNumbers() throws Exception {

        // Arrange
        PowerMockito.doNothing().when(MemoryStatic.class, "addToValue", ARGUMENT);
        PowerMockito.when(MemoryStatic.getCurrentValue()).thenReturn(ADDITION_RESULT_TRIPLE);

        int expectedResult = ADDITION_RESULT_TRIPLE;

        // Act
        int actualResult = mCalculatorStatic.tripleAddition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);

        PowerMockito.verifyStatic(MemoryStatic.class, Mockito.times(3));
        MemoryStatic.addToValue(ARGUMENT);

        PowerMockito.verifyStatic(MemoryStatic.class, Mockito.times(3));
        MemoryStatic.getCurrentValue();
    }
}