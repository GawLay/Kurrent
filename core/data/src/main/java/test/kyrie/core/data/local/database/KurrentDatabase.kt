package test.kyrie.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import test.kyrie.core.data.local.dao.CurrencyDao
import test.kyrie.core.data.local.entity.CurrencyEntity

@Database(
    entities = [CurrencyEntity::class],
    version = 1,
    exportSchema = true
)
abstract class KurrentDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        const val DATABASE_NAME = "kurrent_database"
    }
}

