package space.lopatkin.spb.helpboardgamecard.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.AddCardViewModel;
import space.lopatkin.spb.helpboardgamecard.ui.catalog.CatalogViewModel;
import space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard.cardedit.CardEditViewModel;
import space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard.HelpcardViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;
    private GetAllHelpcardsUseCase getAllHelpcardsUseCase;
    private DeleteHelpcardUseCase deleteHelpcardUseCase;
    private DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase;
    private UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase;
    private DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase;
    private SaveNewHelpcardUseCase saveNewHelpcardUseCase;

    public ViewModelFactory(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase, GetAllHelpcardsUseCase getAllHelpcardsUseCase, DeleteHelpcardUseCase deleteHelpcardUseCase, DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase, UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase, DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase, SaveNewHelpcardUseCase saveNewHelpcardUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
        this.getAllHelpcardsUseCase = getAllHelpcardsUseCase;
        this.deleteHelpcardUseCase = deleteHelpcardUseCase;
        this.deleteHelpcardByIdUseCase = deleteHelpcardByIdUseCase;
        this.updateHelpcardByObjectUseCase = updateHelpcardByObjectUseCase;
        this.deleteHelpcardsByLockUseCase = deleteHelpcardsByLockUseCase;
        this.saveNewHelpcardUseCase = saveNewHelpcardUseCase;
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
            return (T) new AddCardViewModel(saveNewHelpcardUseCase);
        } else if (modelClass.isAssignableFrom(CardEditViewModel.class)) {
            return (T) new CardEditViewModel(getDetailsHelpcardByBoardGameIdUseCase, updateHelpcardByObjectUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass);
    }

}
