package space.lopatkin.spb.helpboardgamecard.presentation.catalog;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUnlockedByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByUnlockStateUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardFavoriteByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardLockingByHelpcardIdUseCase;

import java.util.List;

public class CatalogViewModel extends ViewModel {

    private GetAllHelpcardsUseCase getAllHelpcardsUseCase;
    private DeleteHelpcardUnlockedByHelpcardIdUseCase deleteHelpcardUnlockedByHelpcardIdUseCase;
    private UpdateHelpcardFavoriteByHelpcardIdUseCase updateHelpcardFavoriteByHelpcardIdUseCase;
    private UpdateHelpcardLockingByHelpcardIdUseCase updateHelpcardLockingByHelpcardIdUseCase;
    private DeleteHelpcardsByUnlockStateUseCase deleteHelpcardsByUnlockStateUseCase;
    private MutableLiveData<Message> messageMutable = new MutableLiveData<>();
    LiveData<Message> message = messageMutable;
    LiveData<List<Helpcard>> listHelpcards;

    public CatalogViewModel(GetAllHelpcardsUseCase getAllHelpcardsUseCase,
                            DeleteHelpcardUnlockedByHelpcardIdUseCase deleteHelpcardUnlockedByHelpcardIdUseCase,
                            UpdateHelpcardFavoriteByHelpcardIdUseCase updateHelpcardFavoriteByHelpcardIdUseCase,
                            UpdateHelpcardLockingByHelpcardIdUseCase updateHelpcardLockingByHelpcardIdUseCase,
                            DeleteHelpcardsByUnlockStateUseCase deleteHelpcardsByUnlockStateUseCase) {
        this.getAllHelpcardsUseCase = getAllHelpcardsUseCase;
        this.deleteHelpcardUnlockedByHelpcardIdUseCase = deleteHelpcardUnlockedByHelpcardIdUseCase;
        this.updateHelpcardFavoriteByHelpcardIdUseCase = updateHelpcardFavoriteByHelpcardIdUseCase;
        this.updateHelpcardLockingByHelpcardIdUseCase = updateHelpcardLockingByHelpcardIdUseCase;
        this.deleteHelpcardsByUnlockStateUseCase = deleteHelpcardsByUnlockStateUseCase;
    }

    public void loadHelpcardsList() {
        listHelpcards = getAllHelpcardsUseCase.execute();
    }

    public void deleteAllUnlockHelpcards() {
        deleteHelpcardsByUnlockStateUseCase.execute();
    }

    public void updateFavorite(Helpcard helpcard) {
        Message messageResponse = updateHelpcardFavoriteByHelpcardIdUseCase.execute(helpcard);
        messageMutable.setValue(messageResponse);
        messageMutable.setValue(Message.POOL_EMPTY);
    }

    public void updateLocking(Helpcard helpcard) {
        Message messageResponse = updateHelpcardLockingByHelpcardIdUseCase.execute(helpcard);
        messageMutable.setValue(messageResponse);
        messageMutable.setValue(Message.POOL_EMPTY);
    }

    public void delete(Helpcard helpcard) {
        Message messageResponse = deleteHelpcardUnlockedByHelpcardIdUseCase.execute(helpcard);
        messageMutable.setValue(messageResponse);
        messageMutable.setValue(Message.POOL_EMPTY);
    }

}
