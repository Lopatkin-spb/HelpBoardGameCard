package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

import java.util.List;

public class GetAllHelpcardsUseCase {

    private AppRepository repository;

    public GetAllHelpcardsUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Helpcard>> execute() {
        return repository.getAllHelpcards();
    }

}
