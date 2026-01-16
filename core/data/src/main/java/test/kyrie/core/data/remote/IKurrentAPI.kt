package test.kyrie.core.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import test.kyrie.core.data.model.ExchangeRatesResponseDto
import test.kyrie.core.data.model.SupportedCurrenciesResponseDto

interface IKurrentApi {

    @GET("v2.0/supported-currencies")
    suspend fun getSupportedCurrencies(): SupportedCurrenciesResponseDto

    @GET("v2.0/rates/latest")
    suspend fun getLatestRates(): ExchangeRatesResponseDto
}
