package test.kyrie.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val countryName: String,
    val countryCode: String,
    val rateToUsd: String,
    val iconUrl: String,
    val isAvailable: Boolean,
    val lastUpdated: Long = System.currentTimeMillis(),
)
