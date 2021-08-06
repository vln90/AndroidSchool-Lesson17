package testExamples.kotlin

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import testExamples.calculator.Calculator
import testExamples.memory.Memory

class CalculatorKotlinTest {

    private lateinit var calculator: Calculator

    // private var memory: Memory = mockk()
    // private var memory = mockk<Memory>()
    private lateinit var memory: Memory

    @Before
    fun setUp() {
        memory = mockk()
        calculator = Calculator(memory)
    }

    @Test
    fun `add number once`() {

        // Arrange
        justRun { memory.addToValue(ARGUMENT) }
        every { memory.currentValue } returns ADDITION_RESULT

        val expectedResult = ADDITION_RESULT

        // Act
        val actualResult = calculator.addition(ARGUMENT)

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
        verify(exactly = 1) { memory.addToValue(ARGUMENT) }
    }

    @Test
    fun `add number three times`() {

        // Arrange
        justRun { memory.addToValue(ARGUMENT) }
        every { memory.currentValue } returns ADDITION_RESULT_TRIPLE
        // можно делать цепочки вызовов, например:
        // every { memory.currentValue.plus(1) } returns ADDITION_RESULT_TRIPLE
        val expectedResult = ADDITION_RESULT_TRIPLE

        // Act
        val actualResult = calculator.tripleAddition(ARGUMENT)


        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
        verify(exactly = 3) { memory.addToValue(ARGUMENT) }
    }

    private companion object {
        const val ARGUMENT = 10
        const val ADDITION_RESULT = 110
        const val ADDITION_RESULT_TRIPLE = 130
    }
}