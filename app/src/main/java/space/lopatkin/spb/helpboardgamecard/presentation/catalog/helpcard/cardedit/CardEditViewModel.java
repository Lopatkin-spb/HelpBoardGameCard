package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;

public class CardEditViewModel extends ViewModel {

    private GetHelpcardByHelpcardIdUseCase getHelpcardByHelpcardIdUseCase;
    private UpdateHelpcardByHelpcardIdUseCase updateHelpcardByHelpcardIdUseCase;
    private GetKeyboardTypeUseCase getKeyboardTypeUseCase;
    private MutableLiveData<Integer> helpcardIdMutable = new MutableLiveData<>();
    private MutableLiveData<KeyboardType> keyboardTypeMutable = new MutableLiveData<>();
    private MutableLiveData<Message> messageMutable = new MutableLiveData<>();
    LiveData<Helpcard> helpcard;
    LiveData<KeyboardType> keyboardType = keyboardTypeMutable;
    LiveData<Message> message = messageMutable;

    public CardEditViewModel(GetHelpcardByHelpcardIdUseCase getHelpcardByHelpcardIdUseCase,
                             UpdateHelpcardByHelpcardIdUseCase updateHelpcardByHelpcardIdUseCase,
                             GetKeyboardTypeUseCase getKeyboardTypeUseCase) {
        this.getHelpcardByHelpcardIdUseCase = getHelpcardByHelpcardIdUseCase;
        this.updateHelpcardByHelpcardIdUseCase = updateHelpcardByHelpcardIdUseCase;
        this.getKeyboardTypeUseCase = getKeyboardTypeUseCase;
    }

    public void loadKeyboardType() {
        keyboardTypeMutable.setValue(getKeyboardTypeUseCase.execute());
    }

    public void loadHelpcard(int helpcardId) {
        helpcardIdMutable.setValue(helpcardId);
        helpcard = Transformations.switchMap(helpcardIdMutable, (thisId) -> getHelpcardByHelpcardIdUseCase.execute(thisId));
    }

    public void update(Helpcard helpcard) {
        Message messageResponse = updateHelpcardByHelpcardIdUseCase.execute(helpcard);
        messageMutable.setValue(messageResponse);
        messageMutable.setValue(Message.POOL_EMPTY);
    }

}