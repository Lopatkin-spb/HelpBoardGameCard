package space.lopatkin.spb.helpboardgamecard.ui.catalog;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.AddNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardByIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByLockUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

public class CatalogViewModel extends ViewModel {

    private GetAllHelpcardsUseCase getAllHelpcardsUseCase;
    private DeleteHelpcardUseCase deleteHelpcardUseCase;
    private DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase;
    private UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase;
    private DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase;
    private AddNewHelpcardUseCase addNewHelpcardUseCase;

    private LiveData<List<Helpcard>> listHelpcards;

    public CatalogViewModel(GetAllHelpcardsUseCase getAllHelpcardsUseCase,
                            DeleteHelpcardUseCase deleteHelpcardUseCase,
                            DeleteHelpcardByIdUseCase deleteHelpcardByIdUseCase,
                            UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase,
                            DeleteHelpcardsByLockUseCase deleteHelpcardsByLockUseCase,
                            AddNewHelpcardUseCase addNewHelpcardUseCase) {
        this.getAllHelpcardsUseCase = getAllHelpcardsUseCase;
        this.deleteHelpcardUseCase = deleteHelpcardUseCase;
        this.deleteHelpcardByIdUseCase = deleteHelpcardByIdUseCase;
        this.updateHelpcardByObjectUseCase = updateHelpcardByObjectUseCase;
        this.deleteHelpcardsByLockUseCase = deleteHelpcardsByLockUseCase;
        this.addNewHelpcardUseCase = addNewHelpcardUseCase;

        listHelpcards = this.getAllHelpcardsUseCase.execute();
    }

    public LiveData<List<Helpcard>> getListHelpcards() {
        return listHelpcards;
    }

    public void delete(Helpcard helpcard) {
        deleteHelpcardUseCase.execute(helpcard);
    }
    public void delete(int id) {
        deleteHelpcardByIdUseCase.execute(id);
    }
    public void update(Helpcard helpcard) {
        updateHelpcardByObjectUseCase.execute(helpcard);
    }
    public void deleteAllUnlockHelpcards() {
        deleteHelpcardsByLockUseCase.execute();
    }
    public void addNewHelpcard(Helpcard helpcard) {
        addNewHelpcardUseCase.execute(helpcard);
    }

}
