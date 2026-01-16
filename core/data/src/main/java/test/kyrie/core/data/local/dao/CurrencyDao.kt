package test.kyrie.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import test.kyrie.core.data.local.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies ORDER BY code ASC")
    fun observeAllCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies WHERE code = :code")
    suspend fun getCurrencyByCode(code: String): CurrencyEntity?

    @Query("SELECT * FROM currencies ORDER BY code ASC")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM currencies")
    suspend fun deleteAllCurrencies()

    /**
     * to ensure atomic replacement of data
     * so that deletion and insertion either both succeed or both fail
     */
    @Transaction
    suspend fun replaceAllCurrencies(currencies: List<CurrencyEntity>) {
        deleteAllCurrencies()
        insertCurrencies(currencies)
    }

    @Query("SELECT COUNT(*) FROM currencies")
    suspend fun getCurrencyCount(): Int
}

