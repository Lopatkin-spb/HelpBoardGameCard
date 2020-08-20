package space.lopatkin.spb.helpboardgamecard.ui.helpCard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpCardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HelpCardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("тут хранится каталог памяток");
    }

    public LiveData<String> getText() {
        return mText;
    }
}