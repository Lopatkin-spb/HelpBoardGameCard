package space.lopatkin.spb.helpboardgamecard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*
import space.lopatkin.spb.helpboardgamecard.presentation.addcard.AddCardViewModel
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.CatalogViewModel
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.HelpcardViewModel
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit.CardEditViewModel
import space.lopatkin.spb.helpboardgamecard.presentation.settings.SettingsViewModel

class ViewModelFactory(
    private val getHelpcardByBoardgameIdUseCase: GetHelpcardByBoardgameIdUseCase,
    private val getAllBoardgamesInfoUseCase: GetAllBoardgamesInfoUseCase,
    private val getBoardgameRawByBoardgameIdUseCase: GetBoardgameRawByBoardgameIdUseCase,
    private val deleteBoardgameUnlockedByBoardgameIdUseCase: DeleteBoardgameUnlockedByBoardgameIdUseCase,
    private val updateBoardgameByBoardgameIdUseCase: UpdateBoardgameByBoardgameIdUseCase,
    private val updateBoardgameFavoriteByBoardgameIdUseCase: UpdateBoardgameFavoriteByBoardgameIdUseCase,
    private val updateBoardgameLockingByBoardgameIdUseCase: UpdateBoardgameLockingByBoardgameIdUseCase,
    private val deleteBoardgamesByUnlockStateUseCase: DeleteBoardgamesByUnlockStateUseCase,
    private val saveBoardgameNewByBoardgameIdUseCase: SaveBoardgameNewByBoardgameIdUseCase,
    private val saveKeyboardTypeByUserChoiceUseCase: SaveKeyboardTypeByUserChoiceUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HelpcardViewModel::class.java)) {
            return HelpcardViewModel(
                getHelpcardByBoardgameIdUseCase = getHelpcardByBoardgameIdUseCase,
                dispatchers = dispatchers
            ) as T
        } else if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            return CatalogViewModel(
                getAllBoardgamesInfoUseCase = getAllBoardgamesInfoUseCase,
                deleteBoardgameUnlockedByBoardgameIdUseCase = deleteBoardgameUnlockedByBoardgameIdUseCase,
                updateBoardgameFavoriteByBoardgameIdUseCase = updateBoardgameFavoriteByBoardgameIdUseCase,
                updateBoardgameLockingByBoardgameIdUseCase = updateBoardgameLockingByBoardgameIdUseCase,
                deleteBoardgamesByUnlockStateUseCase = deleteBoardgamesByUnlockStateUseCase,
                dispatchers = dispatchers
            ) as T
        } else if (modelClass.isAssignableFrom(AddCardViewModel::class.java)) {
            return AddCardViewModel(
                saveBoardgameNewByBoardgameIdUseCase = saveBoardgameNewByBoardgameIdUseCase,
                getKeyboardTypeUseCase = getKeyboardTypeUseCase,
                dispatchers = dispatchers
            ) as T
        } else if (modelClass.isAssignableFrom(CardEditViewModel::class.java)) {
            return CardEditViewModel(
                getBoardgameRawByBoardgameIdUseCase = getBoardgameRawByBoardgameIdUseCase,
                updateBoardgameByBoardgameIdUseCase = updateBoardgameByBoardgameIdUseCase,
                getKeyboardTypeUseCase = getKeyboardTypeUseCase,
                dispatchers = dispatchers
            ) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                saveKeyboardTypeByUserChoiceUseCase = saveKeyboardTypeByUserChoiceUseCase,
                getKeyboardTypeUseCase = getKeyboardTypeUseCase,
                dispatchers = dispatchers
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }

}
