package space.lopatkin.spb.helpboardgamecard.di

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import space.lopatkin.spb.helpboardgamecard.data.repository.BoardgameRepositoryImpl
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.SettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.local.room.RoomBoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.local.preferences.PreferencesSettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepositoryImpl
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideBoardgameRepository(
        local: BoardgameLocalDataSource,
    ): BoardgameRepository {
        return BoardgameRepositoryImpl(
            boardgameLocalDataSource = local,
        )
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(
        local: SettingsLocalDataSource
    ): SettingsRepository {
        return SettingsRepositoryImpl(
            settingsLocalDataSource = local
        )
    }

    @Singleton
    @Provides
    fun provideBoardgameLocalDataSource(context: Context, scope: CoroutineScope): BoardgameLocalDataSource {
        return RoomBoardgameLocalDataSource(context = context, scope = scope)
    }

    @Singleton
    @Provides
    fun provideSettingsLocalDataSource(context: Context): SettingsLocalDataSource {
        return PreferencesSettingsLocalDataSource(context = context)
    }

}
