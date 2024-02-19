package space.lopatkin.spb.helpboardgamecard.di

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val context: Context,
    private val scope: CoroutineScope
) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideScope(): CoroutineScope {
        return scope
    }

    @Singleton
    @Provides
    fun provideDispatchers(): CoroutineDispatchers {
        return CoroutineDispatchers()
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
        getKeyboardTypeUseCase: GetKeyboardTypeUseCase,
        dispatchers: CoroutineDispatchers
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
            getKeyboardTypeUseCase = getKeyboardTypeUseCase,
            dispatchers = dispatchers
        )
    }

    class CoroutineDispatchers(
        val main: MainCoroutineDispatcher = Dispatchers.Main,
        val default: CoroutineDispatcher = Dispatchers.Default,
        val io: CoroutineDispatcher = Dispatchers.IO,
        val unconfined: CoroutineDispatcher = Dispatchers.Unconfined,
    )

}
