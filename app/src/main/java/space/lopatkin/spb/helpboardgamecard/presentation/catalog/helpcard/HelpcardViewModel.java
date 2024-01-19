package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

public class HelpcardViewModel extends ViewModel {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;
    private MutableLiveData<Integer> idMutableLiveData = new MutableLiveData<>();

    public HelpcardViewModel(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
    }

    public void setId(int boardGameId) {
        idMutableLiveData.setValue(boardGameId);
    }

    public LiveData<Helpcard> helpcardLiveData = Transformations
            .switchMap(idMutableLiveData, (id) -> getDetailsHelpcardByBoardGameIdUseCase.execute(id));

    public LiveData<Integer> getCardId() {
        return idMutableLiveData;
    }

}
