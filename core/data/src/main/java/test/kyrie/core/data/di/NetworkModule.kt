package test.kyrie.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.kyrie.core.data.remote.IKurrentApi
import test.kyrie.core.data.remote.interceptor.ApiKeyInterceptor
import test.kyrie.core.data.remote.interceptor.ApiKeyProvider
import test.kyrie.core.data.remote.interceptor.CryptoApiKeyProvider
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.currencyfreaks.com/"
    private const val TIMEOUT_SECONDS = 30L

    @Provides
    @Singleton
    fun provideApiKeyProvider(): ApiKeyProvider = CryptoApiKeyProvider()

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(apiKeyProvider: ApiKeyProvider): ApiKeyInterceptor = ApiKeyInterceptor(apiKeyProvider)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideKurrentApi(retrofit: Retrofit): IKurrentApi = retrofit.create(IKurrentApi::class.java)
}
