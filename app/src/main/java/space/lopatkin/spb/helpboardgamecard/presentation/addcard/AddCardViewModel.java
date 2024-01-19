package space.lopatkin.spb.helpboardgamecard.presentation.addcard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;

public class AddCardViewModel extends ViewModel {

    private SaveNewHelpcardUseCase saveNewHelpcardUseCase;
    private GetKeyboardTypeUseCase getKeyboardTypeUseCase;
    private MutableLiveData<KeyboardType> keyboardTypeMutable = new MutableLiveData<>();
    LiveData<KeyboardType> keyboardType = keyboardTypeMutable;

    public AddCardViewModel(SaveNewHelpcardUseCase saveNewHelpcardUseCase,
                            GetKeyboardTypeUseCase getKeyboardTypeUseCase) {
        this.saveNewHelpcardUseCase = saveNewHelpcardUseCase;
        this.getKeyboardTypeUseCase = getKeyboardTypeUseCase;
    }

    public void loadKeyboardType() {
        keyboardTypeMutable.setValue(getKeyboardTypeUseCase.execute());
    }

    public void saveNewHelpcard(Helpcard helpcard) {
        saveNewHelpcardUseCase.execute(helpcard);
    }

}
