package test.kyrie.core.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor to add API key to requests that require
 */
class ApiKeyInterceptor @Inject constructor(
    private val apiKeyProvider: ApiKeyProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url

        // Check if the request already has an apikey parameter
        // If it does, replace it with our decrypted key
        // If it doesn't and the endpoint needs it, add it
        val needsApiKey = url.encodedPath.contains("/rates/")

        return if (needsApiKey || url.queryParameter("apikey") != null) {
            val newUrl = url.newBuilder()
                .removeAllQueryParameters("apikey")
                .addQueryParameter("apikey", apiKeyProvider.getApiKey())
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}

interface ApiKeyProvider {
    fun getApiKey(): String
}

