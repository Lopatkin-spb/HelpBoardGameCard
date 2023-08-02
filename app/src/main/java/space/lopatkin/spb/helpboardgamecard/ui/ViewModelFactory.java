package space.lopatkin.spb.helpboardgamecard.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.AddNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardByIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByLockUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;
import space.lopatkin.spb.helpboardgamecard.ui.catalog.CatalogViewModel;
import space.lopatkin.spb.helpboardgamecard.ui.helpcard.HelpcardViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;
    private GetAllHelpcardsUseCase getAllHelpcardsUseCase;
    private DeleteHelpcardUseCase deleteHelpcardUseCase;
    private DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase;
    private UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase;
    private DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase;
    private AddNewHelpcardUseCase addNewHelpcardUseCase;

    public ViewModelFactory(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase, GetAllHelpcardsUseCase getAllHelpcardsUseCase, DeleteHelpcardUseCase deleteHelpcardUseCase, DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase, UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase, DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase, AddNewHelpcardUseCase addNewHelpcardUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
        this.getAllHelpcardsUseCase = getAllHelpcardsUseCase;
        this.deleteHelpcardUseCase = deleteHelpcardUseCase;
        this.deleteHelpcardByIdUseCase = deleteHelpcardByIdUseCase;
        this.updateHelpcardByObjectUseCase = updateHelpcardByObjectUseCase;
        this.deleteHelpcardsByLockUseCase = deleteHelpcardsByLockUseCase;
        this.addNewHelpcardUseCase = addNewHelpcardUseCase;
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
                    deleteHelpcardsByLockUseCase, addNewHelpcardUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass);
    }

}
