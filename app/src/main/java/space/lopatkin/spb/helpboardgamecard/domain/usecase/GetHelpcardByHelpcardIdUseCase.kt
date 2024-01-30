package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;

public class GetHelpcardByHelpcardIdUseCase {

    private AppRepository repository;

    public GetHelpcardByHelpcardIdUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public LiveData<Helpcard> execute(int boardGameId) {
        return repository.getHelpcard(boardGameId);
    }

}
