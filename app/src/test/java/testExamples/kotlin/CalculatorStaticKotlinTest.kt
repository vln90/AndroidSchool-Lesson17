package testExamples.kotlin

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import testExamples.calculator.CalculatorStatic
import testExamples.memory.MemoryStatic

class CalculatorStaticKotlinTest {

    private lateinit var calculator: CalculatorStatic

    @Before
    fun setUp() {
        mockkStatic(MemoryStatic::class)
        calculator = CalculatorStatic()
    }

    @After
    fun clean() {
        unmockkStatic(MemoryStatic::class)
    }

    @Test
    fun `add number once`() {

        // Arrange
        justRun { MemoryStatic.addToValue(ARGUMENT) }
        every { MemoryStatic.getCurrentValue() } returns ADDITION_RESULT

        val expectedResult = ADDITION_RESULT

        // Act
        val actualResult = calculator.addition(ARGUMENT)

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)

        verify(exactly = 1) { MemoryStatic.addToValue(ARGUMENT) }
        verify(exactly = 1) { MemoryStatic.getCurrentValue() }
    }

    @Test
    fun `add number three times`() {

        // Arrange
        justRun { MemoryStatic.addToValue(ARGUMENT) }
        every { MemoryStatic.getCurrentValue() } returns ADDITION_RESULT_TRIPLE

        val expectedResult = ADDITION_RESULT_TRIPLE

        // Act
        val actualResult = calculator.tripleAddition(ARGUMENT)

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)

        verify(exactly = 3) { MemoryStatic.addToValue(ARGUMENT) }
        verify(exactly = 3) { MemoryStatic.getCurrentValue() }
    }

    companion object {
        private const val ARGUMENT = 10
        private const val ADDITION_RESULT = 110
        private const val ADDITION_RESULT_TRIPLE = 130
    }
}
