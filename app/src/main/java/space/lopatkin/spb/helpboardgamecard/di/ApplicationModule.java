package space.lopatkin.spb.helpboardgamecard.di;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardByIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByLockUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private Application applicationContext;

    public ApplicationModule(Application applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return applicationContext;
    }

    @Singleton
    @Provides
    public ViewModelFactory provideViewModelFactory(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase,
                                                    GetAllHelpcardsUseCase getAllHelpcardsUseCase,
                                                    DeleteHelpcardUseCase deleteHelpcardUseCase,
                                                    DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase,
                                                    UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase,
                                                    DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase,
                                                    SaveNewHelpcardUseCase saveNewHelpcardUseCase,
                                                    SaveKeyboardTypeByUserChoiceUseCase saveKeyboardTypeByUserChoiceUseCase,
                                                    GetKeyboardTypeUseCase getKeyboardTypeUseCase) {
        return new ViewModelFactory(getDetailsHelpcardByBoardGameIdUseCase,
                getAllHelpcardsUseCase,
                deleteHelpcardUseCase,
                deleteHelpcardByIdUseCase,
                updateHelpcardByObjectUseCase,
                deleteHelpcardsByLockUseCase,
                saveNewHelpcardUseCase,
                saveKeyboardTypeByUserChoiceUseCase,
                getKeyboardTypeUseCase
        );
    }

}
