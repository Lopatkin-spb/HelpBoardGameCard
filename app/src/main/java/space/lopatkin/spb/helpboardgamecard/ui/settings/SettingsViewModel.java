package space.lopatkin.spb.helpboardgamecard.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.ui.Adapter;

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