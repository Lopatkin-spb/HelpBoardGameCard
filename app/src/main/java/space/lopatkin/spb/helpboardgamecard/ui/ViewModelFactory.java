package space.lopatkin.spb.helpboardgamecard.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.lopatkin.spb.helpboardgamecard.dataRoom.HelpcardRepositoryImpl;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.ui.helpcard.HelpcardViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Application application = new Application();
    private HelpcardRepositoryImpl repositoryImpl = new HelpcardRepositoryImpl(application);
    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase = new GetDetailsHelpcardByBoardGameIdUseCase(repositoryImpl);

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        return null;
        return (T) new HelpcardViewModel(getDetailsHelpcardByBoardGameIdUseCase);
    }

}
