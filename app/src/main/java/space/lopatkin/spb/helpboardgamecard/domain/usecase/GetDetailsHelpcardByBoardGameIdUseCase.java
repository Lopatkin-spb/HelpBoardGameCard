package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

public class GetDetailsHelpcardByBoardGameIdUseCase {

    private AppRepository repository;

    public GetDetailsHelpcardByBoardGameIdUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public LiveData<Helpcard> execute(int boardGameId) {
        return repository.getHelpcard(boardGameId);
    }

}
