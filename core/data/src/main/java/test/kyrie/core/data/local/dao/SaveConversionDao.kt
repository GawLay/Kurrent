package test.kyrie.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import test.kyrie.core.data.local.entity.SaveConversionEntity

@Dao
interface SaveConversionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedConversionCurrency(currency: SaveConversionEntity)

    @Query("SELECT * FROM save_conversions LIMIT 1")
    suspend fun getSavedConversionCurrency(): SaveConversionEntity?

    @Query("DELETE FROM save_conversions")
    suspend fun deleteSavedConversions()

    @Transaction
    suspend fun replaceSavedConversionCurrency(currency: SaveConversionEntity) {
        deleteSavedConversions()
        insertSavedConversionCurrency(currency)
    }

}