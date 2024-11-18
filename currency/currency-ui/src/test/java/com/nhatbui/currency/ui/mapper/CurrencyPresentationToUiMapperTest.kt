package com.nhatbui.currency.ui.mapper

import com.nhatbui.currency.presentation.model.CurrencyPresentationModel
import com.nhatbui.currency.ui.model.CurrencyUiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class CurrencyPresentationToUiMapperTest(
    private val input: CurrencyPresentationModel,
    private val output: CurrencyUiModel
) {
    companion object {

        @JvmStatic
        @Parameters(name = "Given member presentation {0} then returns expectedResult {1}")
        fun parameters() = listOf(
            arrayOf(
                CurrencyPresentationModel.Crypto("id1", "name1", "symbol1"),
                CurrencyUiModel.Crypto("id1", "name1", "symbol1", "n")
            ),
            arrayOf(
                CurrencyPresentationModel.Fiat("id2", "fiat2", "symbol2", "code2"),
                CurrencyUiModel.Fiat("id2", "fiat2", "symbol2", "f", "code2")
            )
        )
    }

    private lateinit var classUnderTest: CurrencyPresentationToUiMapper

    @Before
    fun setup() {
        classUnderTest = CurrencyPresentationToUiMapper()
    }

    @Test
    fun `When map then returns expected result`() {
        // When
        val actualValue = classUnderTest.map(input)

        // Then
        assertEquals(output, actualValue)
    }
}
