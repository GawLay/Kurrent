package test.kyrie.core.domain.model

data class SaveConversionDomain(
    val fromAmount: String,
    val fromCurrency: String,
    val toAmount: String,
    val toCurrency: String,
    val timestamp: Long
)
