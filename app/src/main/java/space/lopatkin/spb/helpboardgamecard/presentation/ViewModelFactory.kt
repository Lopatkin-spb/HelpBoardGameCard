package space.lopatkin.spb.helpboardgamecard.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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
import space.lopatkin.spb.helpboardgamecard.presentation.addcard.AddCardViewModel;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.CatalogViewModel;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.HelpcardViewModel;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit.CardEditViewModel;
import space.lopatkin.spb.helpboardgamecard.presentation.settings.SettingsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private GetHelpcardByHelpcardIdUseCase getHelpcardByHelpcardIdUseCase;
    private GetAllHelpcardsUseCase getAllHelpcardsUseCase;
    private DeleteHelpcardUnlockedByHelpcardIdUseCase deleteHelpcardUnlockedByHelpcardIdUseCase;
    private UpdateHelpcardByHelpcardIdUseCase updateHelpcardByHelpcardIdUseCase;
    private UpdateHelpcardFavoriteByHelpcardIdUseCase updateHelpcardFavoriteByHelpcardIdUseCase;
    private UpdateHelpcardLockingByHelpcardIdUseCase updateHelpcardLockingByHelpcardIdUseCase;
    private DeleteHelpcardsByUnlockStateUseCase deleteHelpcardsByUnlockStateUseCase;
    private SaveHelpcardNewByHelpcardIdUseCase saveHelpcardNewByHelpcardIdUseCase;
    private SaveKeyboardTypeByUserChoiceUseCase saveKeyboardTypeByUserChoiceUseCase;
    private GetKeyboardTypeUseCase getKeyboardTypeUseCase;


    private DeleteHelpcardUseCase deleteHelpcardUseCase;

    public ViewModelFactory(GetHelpcardByHelpcardIdUseCase getHelpcardByHelpcardIdUseCase,
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
        this.getHelpcardByHelpcardIdUseCase = getHelpcardByHelpcardIdUseCase;
        this.getAllHelpcardsUseCase = getAllHelpcardsUseCase;
        this.deleteHelpcardUseCase = deleteHelpcardUseCase;
        this.deleteHelpcardUnlockedByHelpcardIdUseCase = deleteHelpcardUnlockedByHelpcardIdUseCase;
        this.updateHelpcardByHelpcardIdUseCase = updateHelpcardByHelpcardIdUseCase;
        this.updateHelpcardFavoriteByHelpcardIdUseCase = updateHelpcardFavoriteByHelpcardIdUseCase;
        this.updateHelpcardLockingByHelpcardIdUseCase = updateHelpcardLockingByHelpcardIdUseCase;
        this.deleteHelpcardsByUnlockStateUseCase = deleteHelpcardsByUnlockStateUseCase;
        this.saveHelpcardNewByHelpcardIdUseCase = saveHelpcardNewByHelpcardIdUseCase;
        this.saveKeyboardTypeByUserChoiceUseCase = saveKeyboardTypeByUserChoiceUseCase;
        this.getKeyboardTypeUseCase = getKeyboardTypeUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HelpcardViewModel.class)) {
            return (T) new HelpcardViewModel(getHelpcardByHelpcardIdUseCase);
        } else if (modelClass.isAssignableFrom(CatalogViewModel.class)) {
            return (T) new CatalogViewModel(getAllHelpcardsUseCase,
                    deleteHelpcardUnlockedByHelpcardIdUseCase,
                    updateHelpcardFavoriteByHelpcardIdUseCase,
                    updateHelpcardLockingByHelpcardIdUseCase,
                    deleteHelpcardsByUnlockStateUseCase);
        } else if (modelClass.isAssignableFrom(AddCardViewModel.class)) {
            return (T) new AddCardViewModel(saveHelpcardNewByHelpcardIdUseCase,
                    getKeyboardTypeUseCase);
        } else if (modelClass.isAssignableFrom(CardEditViewModel.class)) {
            return (T) new CardEditViewModel(getHelpcardByHelpcardIdUseCase,
                    updateHelpcardByHelpcardIdUseCase,
                    getKeyboardTypeUseCase);
        } else if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
            return (T) new SettingsViewModel(saveKeyboardTypeByUserChoiceUseCase,
                    getKeyboardTypeUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass);
    }

}
