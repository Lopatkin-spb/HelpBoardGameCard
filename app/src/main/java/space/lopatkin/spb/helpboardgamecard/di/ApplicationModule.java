package space.lopatkin.spb.helpboardgamecard.di;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUnlockedByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByUnlockStateUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveHelpcardNewByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardFavoriteByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardLockingByHelpcardIdUseCase;
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
    public ViewModelFactory provideViewModelFactory(GetHelpcardByHelpcardIdUseCase getHelpcardByHelpcardIdUseCase,
                                                    GetAllHelpcardsUseCase getAllHelpcardsUseCase,
                                                    DeleteHelpcardUseCase deleteHelpcardUseCase,
                                                    DeleteHelpcardUnlockedByHelpcardIdUseCase deleteHelpcardUnlockedByHelpcardIdUseCase,
                                                    UpdateHelpcardByHelpcardIdUseCase updateHelpcardByHelpcardIdUseCase,
                                                    UpdateHelpcardFavoriteByHelpcardIdUseCase updateHelpcardFavoriteByHelpcardIdUseCase,
                                                    UpdateHelpcardLockingByHelpcardIdUseCase updateHelpcardLockingByHelpcardIdUseCase,
                                                    DeleteHelpcardsByUnlockStateUseCase deleteHelpcardsByUnlockStateUseCase,
                                                    SaveHelpcardNewByHelpcardIdUseCase saveHelpcardNewByHelpcardIdUseCase,
                                                    SaveKeyboardTypeByUserChoiceUseCase saveKeyboardTypeByUserChoiceUseCase,
                                                    GetKeyboardTypeUseCase getKeyboardTypeUseCase) {
        return new ViewModelFactory(getHelpcardByHelpcardIdUseCase,
                getAllHelpcardsUseCase,
                deleteHelpcardUseCase,
                deleteHelpcardUnlockedByHelpcardIdUseCase,
                updateHelpcardByHelpcardIdUseCase,
                updateHelpcardFavoriteByHelpcardIdUseCase,
                updateHelpcardLockingByHelpcardIdUseCase,
                deleteHelpcardsByUnlockStateUseCase,
                saveHelpcardNewByHelpcardIdUseCase,
                saveKeyboardTypeByUserChoiceUseCase,
                getKeyboardTypeUseCase
        );
    }

}
