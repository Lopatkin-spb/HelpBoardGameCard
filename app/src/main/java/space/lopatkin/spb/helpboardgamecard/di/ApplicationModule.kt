package space.lopatkin.spb.helpboardgamecard.di

import android.app.Application
import dagger.Module
import dagger.Provides
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val applicationContext: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return applicationContext
    }

    @Singleton
    @Provides
    fun provideViewModelFactory(
        getHelpcardByHelpcardIdUseCase: GetHelpcardByHelpcardIdUseCase,
        getAllHelpcardsUseCase: GetAllHelpcardsUseCase,
        deleteHelpcardUseCase: DeleteHelpcardUseCase,
        deleteHelpcardUnlockedByHelpcardIdUseCase: DeleteHelpcardUnlockedByHelpcardIdUseCase,
        updateHelpcardByHelpcardIdUseCase: UpdateHelpcardByHelpcardIdUseCase,
        updateHelpcardFavoriteByHelpcardIdUseCase: UpdateHelpcardFavoriteByHelpcardIdUseCase,
        updateHelpcardLockingByHelpcardIdUseCase: UpdateHelpcardLockingByHelpcardIdUseCase,
        deleteHelpcardsByUnlockStateUseCase: DeleteHelpcardsByUnlockStateUseCase,
        saveHelpcardNewByHelpcardIdUseCase: SaveHelpcardNewByHelpcardIdUseCase,
        saveKeyboardTypeByUserChoiceUseCase: SaveKeyboardTypeByUserChoiceUseCase,
        getKeyboardTypeUseCase: GetKeyboardTypeUseCase
    ): ViewModelFactory {
        return ViewModelFactory(
            getHelpcardByHelpcardIdUseCase = getHelpcardByHelpcardIdUseCase,
            getAllHelpcardsUseCase = getAllHelpcardsUseCase,
            deleteHelpcardUseCase = deleteHelpcardUseCase,
            deleteHelpcardUnlockedByHelpcardIdUseCase = deleteHelpcardUnlockedByHelpcardIdUseCase,
            updateHelpcardByHelpcardIdUseCase = updateHelpcardByHelpcardIdUseCase,
            updateHelpcardFavoriteByHelpcardIdUseCase = updateHelpcardFavoriteByHelpcardIdUseCase,
            updateHelpcardLockingByHelpcardIdUseCase = updateHelpcardLockingByHelpcardIdUseCase,
            deleteHelpcardsByUnlockStateUseCase = deleteHelpcardsByUnlockStateUseCase,
            saveHelpcardNewByHelpcardIdUseCase = saveHelpcardNewByHelpcardIdUseCase,
            saveKeyboardTypeByUserChoiceUseCase = saveKeyboardTypeByUserChoiceUseCase,
            getKeyboardTypeUseCase = getKeyboardTypeUseCase
        )
    }

}
