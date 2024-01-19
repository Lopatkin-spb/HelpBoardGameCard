package space.lopatkin.spb.helpboardgamecard.di;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepository;
import space.lopatkin.spb.helpboardgamecard.data.storage.SettingsRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.dataRoom.HelpcardRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;

import javax.inject.Singleton;

@Module
public class DataModule {

    @Singleton
    @Provides
    public HelpcardRepository provideHelpcardRepository(Application application,
                                                        SettingsRepository settingsRepository) {
        return new HelpcardRepositoryImpl(application, settingsRepository);
    }

    @Singleton
    @Provides
    public SettingsRepository provideSettingsRepository(Application application) {
        return new SettingsRepositoryImpl(application);
    }

}
