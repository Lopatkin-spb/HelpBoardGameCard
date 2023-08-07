package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

public class GetAllHelpcardsUseCase {

    private HelpcardRepository repository;

    public GetAllHelpcardsUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Helpcard>> execute() {
        return repository.getAllHelpcards();
    }

}
