package test.kyrie.core.data.model

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponseDto(
    @SerializedName("date")
    val date: String?,
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Map<String, String>,
)
