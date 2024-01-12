package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard.cardedit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardVariantUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardVariant;

public class CardEditViewModel extends ViewModel {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;
    private UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase;
    private GetKeyboardVariantUseCase getKeyboardVariantUseCase;
    private MutableLiveData<Integer> cardId = new MutableLiveData<>();
    private MutableLiveData<KeyboardVariant> keyboardVariant;

    public CardEditViewModel(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase,
                             UpdateHelpcardByObjectUseCase updateHelpcardByObjectUseCase,
                             GetKeyboardVariantUseCase getKeyboardVariantUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
        this.updateHelpcardByObjectUseCase = updateHelpcardByObjectUseCase;
        this.getKeyboardVariantUseCase = getKeyboardVariantUseCase;

        keyboardVariant = new MutableLiveData<>();
    }

    public void setCardId(int id) {
        cardId.setValue(id);
    }

    public LiveData<Helpcard> getCardDetails = Transformations
            .switchMap(cardId, (id) -> getDetailsHelpcardByBoardGameIdUseCase.execute(id));

    public void update(Helpcard helpcard) {
        updateHelpcardByObjectUseCase.execute(helpcard);
    }

    public LiveData<KeyboardVariant> getKeyboardVariant() {
        setKeyboardVariant(this.getKeyboardVariantUseCase.execute());
        return keyboardVariant;
    }

    private void setKeyboardVariant(KeyboardVariant variant) {
        this.keyboardVariant.setValue(variant);
    }

}