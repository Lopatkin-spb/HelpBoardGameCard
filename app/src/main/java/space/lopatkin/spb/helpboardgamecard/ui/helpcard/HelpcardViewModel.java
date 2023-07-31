package space.lopatkin.spb.helpboardgamecard.ui.helpcard;


import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.dataRoom.HelpcardRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class HelpcardViewModel extends ViewModel {

    private HelpcardRepositoryImpl repositoryImpl;
    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;


    public HelpcardViewModel() {
        Application application = new Application();
        repositoryImpl = new HelpcardRepositoryImpl(application);
        getDetailsHelpcardByBoardGameIdUseCase = new GetDetailsHelpcardByBoardGameIdUseCase(repositoryImpl);
    }

    public LiveData<Helpcard> loadHelpcard(int boardGameId) {
        return getDetailsHelpcardByBoardGameIdUseCase.execute(boardGameId);
    }

}
