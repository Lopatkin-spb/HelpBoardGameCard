package space.lopatkin.spb.helpboardgamecard.presentation.addcard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveHelpcardNewByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;

public class AddCardViewModel extends ViewModel {

    private SaveHelpcardNewByHelpcardIdUseCase saveHelpcardNewByHelpcardIdUseCase;
    private GetKeyboardTypeUseCase getKeyboardTypeUseCase;
    private MutableLiveData<KeyboardType> keyboardTypeMutable = new MutableLiveData<>();
    private MutableLiveData<Message> messageMutable = new MutableLiveData<>();
    LiveData<KeyboardType> keyboardType = keyboardTypeMutable;
    LiveData<Message> message = messageMutable;

    public AddCardViewModel(SaveHelpcardNewByHelpcardIdUseCase saveHelpcardNewByHelpcardIdUseCase,
                            GetKeyboardTypeUseCase getKeyboardTypeUseCase) {
        this.saveHelpcardNewByHelpcardIdUseCase = saveHelpcardNewByHelpcardIdUseCase;
        this.getKeyboardTypeUseCase = getKeyboardTypeUseCase;
    }

    public void loadKeyboardType() {
        keyboardTypeMutable.setValue(getKeyboardTypeUseCase.execute());
    }

    public void saveNewHelpcard(Helpcard helpcard) {
        Message messageResponse = saveHelpcardNewByHelpcardIdUseCase.execute(helpcard);
        messageMutable.setValue(messageResponse);
        messageMutable.setValue(Message.POOL_EMPTY);
    }

}
