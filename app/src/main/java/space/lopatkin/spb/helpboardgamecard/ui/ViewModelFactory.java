package space.lopatkin.spb.helpboardgamecard.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.ui.helpcard.HelpcardViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase;

    public ViewModelFactory(GetDetailsHelpcardByBoardGameIdUseCase getDetailsHelpcardByBoardGameIdUseCase) {
        this.getDetailsHelpcardByBoardGameIdUseCase = getDetailsHelpcardByBoardGameIdUseCase;
    }

    //возможно переделать в дженерик в будущем
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        return null;
        if (modelClass.isAssignableFrom(HelpcardViewModel.class)) {
            return (T) new HelpcardViewModel(getDetailsHelpcardByBoardGameIdUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class " + modelClass);
    }

}
