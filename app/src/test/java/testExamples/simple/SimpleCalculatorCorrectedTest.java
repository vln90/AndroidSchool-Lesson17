package testExamples.simple;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Тестирование {@link SimpleCalculator}
 */
public class SimpleCalculatorCorrectedTest {

    private static final int FIRST_ARGUMENT = 10;
    private static final int SECOND_ARGUMENT = 5;
    private static final int ZERO_ARGUMENT = 0;
    private static final int SUM_RESULT = 15;
    private static final int DIVISION_RESULT = 2;
    private static final String EXCEPTION_MESSAGE = "/ by zero";

    private SimpleCalculator mSimpleCalculator;

    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    @Before
    public void setUp() {
        mSimpleCalculator = new SimpleCalculator();
    }

    @Test
    public void testSum() {

        // Arrange
        int expectedResult = SUM_RESULT;

        // Act
        int result = mSimpleCalculator.sum(FIRST_ARGUMENT, SECOND_ARGUMENT);

        // Asset
        assertEquals(result, expectedResult);
    }

    @Test
    public void testDivision() {

        // Arrange
        int expectedResult = DIVISION_RESULT;

        // Act
        int result = mSimpleCalculator.division(FIRST_ARGUMENT, SECOND_ARGUMENT);

        // Asset
        assertEquals(result, expectedResult);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {

        // Act
        mSimpleCalculator.division(FIRST_ARGUMENT, ZERO_ARGUMENT);
    }

    @Test
    public void testDivisionByZero_var2() {

        // Act
        try {
            mSimpleCalculator.division(FIRST_ARGUMENT, ZERO_ARGUMENT);
        } catch (ArithmeticException ex) {
            assertEquals(EXCEPTION_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void testDivisionByZero_var3() {

        // Arrange
        mExpectedException.expect(ArithmeticException.class);
        mExpectedException.expectMessage(EXCEPTION_MESSAGE);

        ArithmeticException expectedResult = new ArithmeticException(EXCEPTION_MESSAGE);

        // Assert
        given(mSimpleCalculator.division(FIRST_ARGUMENT, ZERO_ARGUMENT))
                .willThrow(expectedResult);

        // when(mSimpleCalculator.division(FIRST_ARGUMENT, ZERO_ARGUMENT))
        //         .thenThrow(expectedResult);
    }
}