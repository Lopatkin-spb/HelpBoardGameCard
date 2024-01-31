package space.lopatkin.spb.helpboardgamecard.di

import android.app.Application
import dagger.Module
import dagger.Provides
import space.lopatkin.spb.helpboardgamecard.data.AppRepositoryImpl
import space.lopatkin.spb.helpboardgamecard.data.repository.DatabaseRepository
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepository
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
        return AppRepositoryImpl(databaseRepository, settingsRepository)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(application: Application): DatabaseRepository {
        return DatabaseRepositoryImpl(application)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(application: Application): SettingsRepository {
        return SettingsRepositoryImpl(application)
    }

}
