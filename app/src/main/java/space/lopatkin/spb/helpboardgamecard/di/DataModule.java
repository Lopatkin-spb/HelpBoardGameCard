package space.lopatkin.spb.helpboardgamecard.di;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.dataRoom.HelpcardRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;

import javax.inject.Singleton;

@Module
public class DataModule {

    @Singleton
    @Provides
    public HelpcardRepository provideHelpcardRepository(Application application) {
        return new HelpcardRepositoryImpl(application);
    }

}
