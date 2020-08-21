package space.lopatkin.spb.helpboardgamecard.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.App;
import space.lopatkin.spb.helpboardgamecard.model.HelpCard;

import java.util.List;

public class MainViewModel extends ViewModel {

    //доступ к списку данных (синглтон тут тоже задействован)



    private LiveData<List<HelpCard>> helpCardLiveData =
            App.getInstance().getHelpCardDao().getAllLiveData();

    public LiveData<List<HelpCard>> getHelpCardLiveData() {
        return helpCardLiveData;
    }
}
