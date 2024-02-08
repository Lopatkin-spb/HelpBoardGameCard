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
        getHelpcardByBoardgameIdUseCase: GetHelpcardByBoardgameIdUseCase,
        getAllBoardgamesInfoUseCase: GetAllBoardgamesInfoUseCase,
        getBoardgameRawByBoardgameIdUseCase: GetBoardgameRawByBoardgameIdUseCase,
        deleteBoardgameUnlockedByBoardgameIdUseCase: DeleteBoardgameUnlockedByBoardgameIdUseCase,
        updateBoardgameByBoardgameIdUseCase: UpdateBoardgameByBoardgameIdUseCase,
        updateBoardgameFavoriteByBoardgameIdUseCase: UpdateBoardgameFavoriteByBoardgameIdUseCase,
        updateBoardgameLockingByBoardgameIdUseCase: UpdateBoardgameLockingByBoardgameIdUseCase,
        deleteBoardgamesByUnlockStateUseCase: DeleteBoardgamesByUnlockStateUseCase,
        saveBoardgameNewByBoardgameIdUseCase: SaveBoardgameNewByBoardgameIdUseCase,
        saveKeyboardTypeByUserChoiceUseCase: SaveKeyboardTypeByUserChoiceUseCase,
        getKeyboardTypeUseCase: GetKeyboardTypeUseCase
    ): ViewModelFactory {
        return ViewModelFactory(
            getHelpcardByBoardgameIdUseCase = getHelpcardByBoardgameIdUseCase,
            getAllBoardgamesInfoUseCase = getAllBoardgamesInfoUseCase,
            getBoardgameRawByBoardgameIdUseCase = getBoardgameRawByBoardgameIdUseCase,
            deleteBoardgameUnlockedByBoardgameIdUseCase = deleteBoardgameUnlockedByBoardgameIdUseCase,
            updateBoardgameByBoardgameIdUseCase = updateBoardgameByBoardgameIdUseCase,
            updateBoardgameFavoriteByBoardgameIdUseCase = updateBoardgameFavoriteByBoardgameIdUseCase,
            updateBoardgameLockingByBoardgameIdUseCase = updateBoardgameLockingByBoardgameIdUseCase,
            deleteBoardgamesByUnlockStateUseCase = deleteBoardgamesByUnlockStateUseCase,
            saveBoardgameNewByBoardgameIdUseCase = saveBoardgameNewByBoardgameIdUseCase,
            saveKeyboardTypeByUserChoiceUseCase = saveKeyboardTypeByUserChoiceUseCase,
            getKeyboardTypeUseCase = getKeyboardTypeUseCase
        )
    }

}
