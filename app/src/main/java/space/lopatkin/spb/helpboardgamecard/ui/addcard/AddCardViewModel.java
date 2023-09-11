package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardVariantUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class AddCardViewModel extends ViewModel {

    private SaveNewHelpcardUseCase saveNewHelpcardUseCase;
    private GetKeyboardVariantUseCase getKeyboardVariantUseCase;
    private MutableLiveData<KeyboardVariant> keyboardVariant;

    public AddCardViewModel(SaveNewHelpcardUseCase saveNewHelpcardUseCase,
                            GetKeyboardVariantUseCase getKeyboardVariantUseCase) {
        this.saveNewHelpcardUseCase = saveNewHelpcardUseCase;
        this.getKeyboardVariantUseCase = getKeyboardVariantUseCase;

        keyboardVariant = new MutableLiveData<>();
    }

    private void setKeyboardVariant(KeyboardVariant variant) {
        this.keyboardVariant.setValue(variant);
    }
    public LiveData<KeyboardVariant> getKeyboardVariant() {
        setKeyboardVariant(this.getKeyboardVariantUseCase.execute());
        return keyboardVariant;
    }
    public void saveNewHelpcard(Helpcard helpcard) {
        saveNewHelpcardUseCase.execute(helpcard);
    }

}
