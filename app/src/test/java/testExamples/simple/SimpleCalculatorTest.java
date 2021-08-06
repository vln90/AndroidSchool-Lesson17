package testExamples.simple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleCalculatorTest {

    @Test
    public void superTest() {

        SimpleCalculator testedClass = new SimpleCalculator();

        assertEquals(15, testedClass.sum(5, 10));
        assertEquals(2, testedClass.division(10, 5));
    }
}