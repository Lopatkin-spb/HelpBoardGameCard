package space.lopatkin.spb.helpboardgamecard.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;




    public SettingsViewModel() {

        //было
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment. тут хранятся настройки");








    }

    public LiveData<String> getText() {
        return mText;
    }
}