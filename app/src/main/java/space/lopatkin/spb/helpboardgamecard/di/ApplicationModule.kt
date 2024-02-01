package space.lopatkin.spb.helpboardgamecard.di

import android.content.Context
import dagger.Module
import dagger.Provides
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
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
