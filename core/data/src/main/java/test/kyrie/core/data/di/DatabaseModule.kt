package test.kyrie.core.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import test.kyrie.core.data.local.dao.CurrencyDao
import test.kyrie.core.data.local.dao.SaveConversionDao
import test.kyrie.core.data.local.database.KurrentDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideKurrentDatabase(
        @ApplicationContext context: Context,
    ): KurrentDatabase =
        Room
            .databaseBuilder(
                context,
                KurrentDatabase::class.java,
                KurrentDatabase.DATABASE_NAME,
            ).fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    @Singleton
    fun provideCurrencyDao(database: KurrentDatabase): CurrencyDao = database.currencyDao()

    @Provides
    @Singleton
    fun provideSaveConversionCurrencyDao(database: KurrentDatabase): SaveConversionDao = database.saveConversionDao()
}
