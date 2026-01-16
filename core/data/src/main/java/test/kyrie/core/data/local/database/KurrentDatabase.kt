package test.kyrie.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import test.kyrie.core.data.local.dao.CurrencyDao
import test.kyrie.core.data.local.dao.SaveConversionDao
import test.kyrie.core.data.local.entity.CurrencyEntity
import test.kyrie.core.data.local.entity.SaveConversionEntity

@Database(
    entities = [CurrencyEntity::class, SaveConversionEntity::class],
    version = 1,
    exportSchema = true
)
abstract class KurrentDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun saveConversionDao(): SaveConversionDao

    companion object {
        const val DATABASE_NAME = "kurrent_database"
    }
}

