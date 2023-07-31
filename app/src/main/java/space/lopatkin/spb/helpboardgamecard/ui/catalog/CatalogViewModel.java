package space.lopatkin.spb.helpboardgamecard.ui.catalog;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.dataRoom.HelpcardRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

public class CatalogViewModel extends AndroidViewModel {
    private HelpcardRepositoryImpl repository;
    private LiveData<List<Helpcard>> allHelpcards;

    public CatalogViewModel(@NonNull Application application) {
        super(application);
        repository = new HelpcardRepositoryImpl(application);
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

    public void deleteAllUnlockHelpcards() {
        repository.deleteAllUnlockHelpcards();
    }

    public LiveData<List<Helpcard>> getAllHelpcards() {
        return allHelpcards;
    }
}
