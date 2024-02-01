package space.lopatkin.spb.helpboardgamecard.di

import android.content.Context
import dagger.Module
import dagger.Provides
import space.lopatkin.spb.helpboardgamecard.data.repository.AppRepositoryImpl
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.DatabaseRepository
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.SettingsRepository
import space.lopatkin.spb.helpboardgamecard.data.storage.database.DatabaseRepositoryImpl
import space.lopatkin.spb.helpboardgamecard.data.storage.preferences.SettingsRepositoryImpl
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideAppRepository(
        databaseRepository: DatabaseRepository,
        settingsRepository: SettingsRepository
    ): AppRepository {
        return AppRepositoryImpl(
            databaseRepository = databaseRepository,
            settingsRepository = settingsRepository
        )
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(context: Context): DatabaseRepository {
        return DatabaseRepositoryImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(context: Context): SettingsRepository {
        return SettingsRepositoryImpl(context = context)
    }

}
