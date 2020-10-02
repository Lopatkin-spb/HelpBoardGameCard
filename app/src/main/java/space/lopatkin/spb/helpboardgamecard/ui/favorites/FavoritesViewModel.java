package space.lopatkin.spb.helpboardgamecard.ui.favorites;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.dataRoom.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

//    //было
//    private MutableLiveData<String> mText;

    private HelpcardRepository repository;
    private LiveData<List<Helpcard>> allFavoritesHelpcards;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new HelpcardRepository(application);
        allFavoritesHelpcards = repository.getAllFavoritesHelpcards();
    }

//проверено

    public void update(Helpcard helpcard) {
        repository.update(helpcard);
    }

    public LiveData<List<Helpcard>> getAllFavoritesHelpcards() {
        return allFavoritesHelpcards;
    }





//        //было
//        mText = new MutableLiveData<>();
//        mText.setValue("This is settings fragment. тут хранятся настройки");



//    //было
//    public LiveData<String> getText() {
//        return mText;

}