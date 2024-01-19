package space.lopatkin.spb.helpboardgamecard.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardByIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByLockUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;
import space.lopatkin.spb.helpboardgamecard.presentation.addcard.AddCardViewModel;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.CatalogViewModel;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.HelpcardViewModel;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit.CardEditViewModel;
import space.lopatkin.spb.helpboardgamecard.presentation.settings.SettingsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;
    private GetAllHelpcardsUseCase getAllHelpcardsUseCase;
    private DeleteHelpcardUseCase deleteHelpcardUseCase;
    private DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase;
    private UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase;
    private DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase;
    private SaveNewHelpcardUseCase saveNewHelpcardUseCase;
    private SaveKeyboardTypeByUserChoiceUseCase saveKeyboardTypeByUserChoiceUseCase;
    private GetKeyboardTypeUseCase getKeyboardTypeUseCase;

    public ViewModelFactory(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase,
                            GetAllHelpcardsUseCase getAllHelpcardsUseCase,
                            DeleteHelpcardUseCase deleteHelpcardUseCase,
                            DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase,
                            UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase,
                            DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase,
                            SaveNewHelpcardUseCase saveNewHelpcardUseCase,
                            SaveKeyboardTypeByUserChoiceUseCase saveKeyboardTypeByUserChoiceUseCase,
                            GetKeyboardTypeUseCase getKeyboardTypeUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
        this.getAllHelpcardsUseCase = getAllHelpcardsUseCase;
        this.deleteHelpcardUseCase = deleteHelpcardUseCase;
        this.deleteHelpcardByIdUseCase = deleteHelpcardByIdUseCase;
        this.updateHelpcardByObjectUseCase = updateHelpcardByObjectUseCase;
        this.deleteHelpcardsByLockUseCase = deleteHelpcardsByLockUseCase;
        this.saveNewHelpcardUseCase = saveNewHelpcardUseCase;
        this.saveKeyboardTypeByUserChoiceUseCase = saveKeyboardTypeByUserChoiceUseCase;
        this.getKeyboardTypeUseCase = getKeyboardTypeUseCase;
    }

    //возможно переделать в дженерик в будущем
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HelpcardViewModel.class)) {
            return (T) new HelpcardViewModel(getDetailsHelpcardByBoardGameIdUseCase);
        } else if (modelClass.isAssignableFrom(CatalogViewModel.class)) {
            return (T) new CatalogViewModel(getAllHelpcardsUseCase, deleteHelpcardUseCase,
                    deleteHelpcardByIdUseCase, updateHelpcardByObjectUseCase,
                    deleteHelpcardsByLockUseCase);
        } else if (modelClass.isAssignableFrom(AddCardViewModel.class)) {
            return (T) new AddCardViewModel(saveNewHelpcardUseCase, getKeyboardTypeUseCase);
        } else if (modelClass.isAssignableFrom(CardEditViewModel.class)) {
            return (T) new CardEditViewModel(getDetailsHelpcardByBoardGameIdUseCase, updateHelpcardByObjectUseCase, getKeyboardTypeUseCase);
        } else if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
            return (T) new SettingsViewModel(saveKeyboardTypeByUserChoiceUseCase, getKeyboardTypeUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass);
    }

}
