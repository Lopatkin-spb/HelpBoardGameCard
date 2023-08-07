package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class GetDetailsHelpcardByBoardGameIdUseCase {

    private HelpcardRepository repository;

    public GetDetailsHelpcardByBoardGameIdUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public LiveData<Helpcard> execute(int boardGameId) {
        return repository.getHelpcard(boardGameId);
    }

}
