package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard.cardedit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class CardEditViewModel extends ViewModel {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;
    private UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase;
    private MutableLiveData<Integer> cardId = new MutableLiveData<>();

    public CardEditViewModel(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase, UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
        this.updateHelpcardByObjectUseCase = updateHelpcardByObjectUseCase;
    }

    public void setCardId(int id) {
        cardId.setValue(id);
    }

    public LiveData<Helpcard> getCardDetails = Transformations
            .switchMap(cardId, (id) -> getDetailsHelpcardByBoardGameIdUseCase.execute(id));

    public void update(Helpcard helpcard) {
        updateHelpcardByObjectUseCase.execute(helpcard);
    }

}