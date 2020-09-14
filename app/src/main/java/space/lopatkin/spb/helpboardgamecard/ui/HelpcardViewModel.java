package space.lopatkin.spb.helpboardgamecard.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.dataRoom.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

public class HelpcardViewModel extends AndroidViewModel {

    private HelpcardRepository repository;
    private LiveData<List<Helpcard>> allHelpcards;


    //test



    public HelpcardViewModel(@NonNull Application application) {
        super(application);
        repository = new HelpcardRepository(application);
        allHelpcards = repository.getAllHelpcards();
    }

    public void insert(Helpcard helpcard) {
        repository.insert(helpcard);
    }
    public void update(Helpcard helpcard) {
        repository.update(helpcard);
    }
    public void delete(Helpcard helpcard) {
        repository.delete(helpcard);
    }
    public void deleteAllHelpcards() {
        repository.deleteAllHelpcards();
    }

//    public void deleteUnlock(Helpcard helpcard) {
//        repository.deleteUnlock(helpcard);
//    }

    public void deleteAllUnlockHelpcards() {
        repository.deleteAllUnlockHelpcards();
    }
    public LiveData<List<Helpcard>> getAllHelpcards() {
        return allHelpcards;
    }
}
