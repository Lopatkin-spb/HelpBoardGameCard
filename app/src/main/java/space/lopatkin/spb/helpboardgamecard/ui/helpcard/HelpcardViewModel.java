package space.lopatkin.spb.helpboardgamecard.ui.helpcard;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class HelpcardViewModel extends ViewModel {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;

    public HelpcardViewModel(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
    }

    //todo: rework to mutable
    public LiveData<Helpcard> loadHelpcard(int boardGameId) {
        return getDetailsHelpcardByBoardGameIdUseCase.execute(boardGameId);
    }

}
