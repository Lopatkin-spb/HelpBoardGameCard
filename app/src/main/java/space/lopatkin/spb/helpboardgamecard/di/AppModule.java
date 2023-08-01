package space.lopatkin.spb.helpboardgamecard.di;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Singleton;

@Module
public class AppModule {

    private Application applicationContext;

    public AppModule(Application applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return applicationContext;
    }

    @Singleton
    @Provides
    public ViewModelFactory provideViewModelFactory(GetDetailsHelpcardByBoardGameIdUseCase useCase) {
        return new ViewModelFactory(useCase);
    }

}
