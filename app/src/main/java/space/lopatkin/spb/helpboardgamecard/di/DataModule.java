package space.lopatkin.spb.helpboardgamecard.di;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.data.repository.DatabaseRepository;
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepository;
import space.lopatkin.spb.helpboardgamecard.data.storage.database.DatabaseRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.data.storage.preferences.SettingsRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.data.AppRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;

import javax.inject.Singleton;

@Module
public class DataModule {

    @Singleton
    @Provides
    public AppRepository provideAppRepository(DatabaseRepository databaseRepository,
                                              SettingsRepository settingsRepository) {
        return new AppRepositoryImpl(databaseRepository, settingsRepository);
    }

    @Singleton
    @Provides
    public DatabaseRepository provideDatabaseRepository(Application application) {
        return new DatabaseRepositoryImpl(application);
    }

    @Singleton
    @Provides
    public SettingsRepository provideSettingsRepository(Application application) {
        return new SettingsRepositoryImpl(application);
    }

}
