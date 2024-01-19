package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;

public class CardEditViewModel extends ViewModel {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;
    private UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase;
    private GetKeyboardTypeUseCase getKeyboardTypeUseCase;
    private MutableLiveData<Integer> cardId = new MutableLiveData<>();
    private MutableLiveData<KeyboardType> keyboardTypeMutable = new MutableLiveData<>();
    LiveData<KeyboardType> keyboardType = keyboardTypeMutable;

    public CardEditViewModel(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase,
                             UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase,
                             GetKeyboardTypeUseCase getKeyboardTypeUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
        this.updateHelpcardByObjectUseCase = updateHelpcardByObjectUseCase;
        this.getKeyboardTypeUseCase = getKeyboardTypeUseCase;
    }

    public void setCardId(int id) {
        cardId.setValue(id);
    }

    public LiveData<Helpcard> getCardDetails = Transformations
            .switchMap(cardId, (id) -> getDetailsHelpcardByBoardGameIdUseCase.execute(id));

    public void update(Helpcard helpcard) {
        updateHelpcardByObjectUseCase.execute(helpcard);
    }

    public void loadKeyboardType() {
        keyboardTypeMutable.setValue(getKeyboardTypeUseCase.execute());
    }

}