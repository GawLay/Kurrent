package test.kyrie.core.data.remote.interceptor

import test.kyrie.core.utility.CryptoHelperBridge
import javax.inject.Inject

/**
 * Implementation of ApiKeyProvider
 * that uses CryptoHelperBridge to decrypt the API key.
 */
class CryptoApiKeyProvider @Inject constructor() : ApiKeyProvider {
    override fun getApiKey(): String {
        return CryptoHelperBridge.getDecryptedApiKey()
    }
}

