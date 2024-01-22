package space.lopatkin.spb.helpboardgamecard.presentation.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase;

public class SettingsViewModel extends ViewModel {
    private SaveKeyboardTypeByUserChoiceUseCase saveKeyboardTypeByUserChoiceUseCase;
    private GetKeyboardTypeUseCase getKeyboardTypeUseCase;
    private MutableLiveData<KeyboardType> keyboardTypeMutable = new MutableLiveData<>();
    private MutableLiveData<Message> messageMutable = new MutableLiveData<>();
    LiveData<Message> message = messageMutable;
    LiveData<KeyboardType> keyboardType = keyboardTypeMutable;

    public SettingsViewModel(SaveKeyboardTypeByUserChoiceUseCase saveKeyboardTypeByUserChoiceUseCase,
                             GetKeyboardTypeUseCase getKeyboardTypeUseCase) {
        this.saveKeyboardTypeByUserChoiceUseCase = saveKeyboardTypeByUserChoiceUseCase;
        this.getKeyboardTypeUseCase = getKeyboardTypeUseCase;
    }

    public void loadKeyboardType() {
        keyboardTypeMutable.setValue(getKeyboardTypeUseCase.execute());
    }

    public void saveKeyboardType(Object userChoice) {
        Message messageResponse = saveKeyboardTypeByUserChoiceUseCase.execute(userChoice);
        messageMutable.setValue(messageResponse);
        messageMutable.setValue(Message.POOL_EMPTY);
    }

}