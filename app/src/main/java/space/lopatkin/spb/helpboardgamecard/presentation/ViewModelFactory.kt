package space.lopatkin.spb.helpboardgamecard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*
import space.lopatkin.spb.helpboardgamecard.presentation.addcard.AddCardViewModel
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.CatalogViewModel
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.HelpcardViewModel
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit.CardEditViewModel
import space.lopatkin.spb.helpboardgamecard.presentation.settings.SettingsViewModel

class ViewModelFactory(
    private val getHelpcardByHelpcardIdUseCase: GetHelpcardByHelpcardIdUseCase,
    private val getAllHelpcardsUseCase: GetAllHelpcardsUseCase,
    private val deleteHelpcardUseCase: DeleteHelpcardUseCase,
    private val deleteHelpcardUnlockedByHelpcardIdUseCase: DeleteHelpcardUnlockedByHelpcardIdUseCase,
    private val updateHelpcardByHelpcardIdUseCase: UpdateHelpcardByHelpcardIdUseCase,
    private val updateHelpcardFavoriteByHelpcardIdUseCase: UpdateHelpcardFavoriteByHelpcardIdUseCase,
    private val updateHelpcardLockingByHelpcardIdUseCase: UpdateHelpcardLockingByHelpcardIdUseCase,
    private val deleteHelpcardsByUnlockStateUseCase: DeleteHelpcardsByUnlockStateUseCase,
    private val saveHelpcardNewByHelpcardIdUseCase: SaveHelpcardNewByHelpcardIdUseCase,
    private val saveKeyboardTypeByUserChoiceUseCase: SaveKeyboardTypeByUserChoiceUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HelpcardViewModel::class.java)) {
            return HelpcardViewModel(
                getHelpcardByHelpcardIdUseCase = getHelpcardByHelpcardIdUseCase
            ) as T
        } else if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            return CatalogViewModel(
                getAllHelpcardsUseCase = getAllHelpcardsUseCase,
                deleteHelpcardUnlockedByHelpcardIdUseCase = deleteHelpcardUnlockedByHelpcardIdUseCase,
                updateHelpcardFavoriteByHelpcardIdUseCase = updateHelpcardFavoriteByHelpcardIdUseCase,
                updateHelpcardLockingByHelpcardIdUseCase = updateHelpcardLockingByHelpcardIdUseCase,
                deleteHelpcardsByUnlockStateUseCase = deleteHelpcardsByUnlockStateUseCase
            ) as T
        } else if (modelClass.isAssignableFrom(AddCardViewModel::class.java)) {
            return AddCardViewModel(
                saveHelpcardNewByHelpcardIdUseCase = saveHelpcardNewByHelpcardIdUseCase,
                getKeyboardTypeUseCase = getKeyboardTypeUseCase
            ) as T
        } else if (modelClass.isAssignableFrom(CardEditViewModel::class.java)) {
            return CardEditViewModel(
                getHelpcardByHelpcardIdUseCase = getHelpcardByHelpcardIdUseCase,
                updateHelpcardByHelpcardIdUseCase = updateHelpcardByHelpcardIdUseCase,
                getKeyboardTypeUseCase = getKeyboardTypeUseCase
            ) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                saveKeyboardTypeByUserChoiceUseCase = saveKeyboardTypeByUserChoiceUseCase,
                getKeyboardTypeUseCase = getKeyboardTypeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }

}
