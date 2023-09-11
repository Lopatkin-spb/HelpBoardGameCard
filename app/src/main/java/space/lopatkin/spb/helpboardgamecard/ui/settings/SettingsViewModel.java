package space.lopatkin.spb.helpboardgamecard.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardVariantUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardVariantUseCase;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardVariant;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<KeyboardVariant> keyboardVariant;
    private SaveKeyboardVariantUseCase saveKeyboardVariantUseCase;
    private GetKeyboardVariantUseCase getKeyboardVariantUseCase;

    public SettingsViewModel(SaveKeyboardVariantUseCase saveKeyboardVariantUseCase,
                             GetKeyboardVariantUseCase getKeyboardVariantUseCase) {
        this.saveKeyboardVariantUseCase = saveKeyboardVariantUseCase;
        this.getKeyboardVariantUseCase = getKeyboardVariantUseCase;

        keyboardVariant = new MutableLiveData<>();
        setKeyboardVariant(this.getKeyboardVariantUseCase.execute());
    }

    public void setKeyboardVariant(KeyboardVariant variant) {
        this.keyboardVariant.setValue(variant);
    }

    public LiveData<KeyboardVariant> getKeyboardVariant() {
        return keyboardVariant;
    }

    public void saveKeyboardVariant(Object variant) {
        KeyboardVariant spinner = KeyboardVariant.getOrdinalFrom(variant.toString());
        setKeyboardVariant(spinner);
        saveKeyboardVariantUseCase.execute(spinner);
    }

}