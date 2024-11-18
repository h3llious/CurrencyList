package com.nhatbui.currency.data.mapper

import com.nhatbui.currency.domain.model.CurrencyDomainModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class CurrencySearchContentResolverTest(
    private val inputData: CurrencyDomainModel,
    private val inputQuery: String,
    private val output: Boolean
) {
    companion object {

        @JvmStatic
        @Parameters(name = "Given member presentation {0} then returns expectedResult {1}")
        fun parameters() = listOf(
            arrayOf(
                CurrencyDomainModel.Crypto("id1", name = "Ethereum Classic", "ETC"),
                "Classic",
                true
            ),
            arrayOf(
                CurrencyDomainModel.Crypto("id1", name = "Ethereum Classic", "ETC"),
                "Ethereum",
                true
            ),
            arrayOf(
                CurrencyDomainModel.Crypto("id2", name = "BET Coin", "BET"),
                "ET",
                false
            )
        )
    }

    private lateinit var classUnderTest: CurrencySearchContentResolver

    @Before
    fun setup() {
        classUnderTest = CurrencySearchContentResolver()
    }

    @Test
    fun `When map then returns expected result`() {
        // When
        val actualValue = classUnderTest.containsQuery(inputData, inputQuery)

        // Then
        assertEquals(output, actualValue)
    }
}
