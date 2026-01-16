package test.kyrie.core.data.model

import com.google.gson.annotations.SerializedName

data class SupportedCurrenciesResponseDto(
    @SerializedName("supportedCurrenciesMap")
    val supportedCurrenciesMap: Map<String, CurrencyDto>
)

data class CurrencyDto(
    @SerializedName("currencyCode")
    val currencyCode: String,

    @SerializedName("currencyName")
    val currencyName: String,

    @SerializedName("countryCode")
    val countryCode: String,

    @SerializedName("countryName")
    val countryName: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("availableFrom")
    val availableFrom: String? = null,

    @SerializedName("icon")
    val icon: String? = null
)