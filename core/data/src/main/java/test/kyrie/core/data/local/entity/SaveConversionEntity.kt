package test.kyrie.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save_conversions")
data class SaveConversionEntity(
    @PrimaryKey
    val fromCurrency: String,
    val fromAmount: String,
    val toAmount: String,
    val toCurrency: String,
    val timestamp: Long,
)
