package test.kyrie.core.theme.manager

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ThemeManagerEntryPoint {
    fun themeManager(): ThemeManager
}
