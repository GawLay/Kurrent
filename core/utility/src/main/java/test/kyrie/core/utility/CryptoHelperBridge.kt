package test.kyrie.core.utility

object CryptoHelperBridge {
    init {
        System.loadLibrary("crypto_helper")
    }

    external fun getXORedResult(
        first: ByteArray,
        second: ByteArray,
    ): String

    fun getDecryptedApiKey(): String = getXORedResult(CryptoValue.API_KEY_BYTE_1, CryptoValue.API_KEY_BYTE_2)
}
