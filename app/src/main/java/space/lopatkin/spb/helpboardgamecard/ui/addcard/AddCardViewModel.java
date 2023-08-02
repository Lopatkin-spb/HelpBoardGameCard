package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class AddCardViewModel extends ViewModel {

    private SaveNewHelpcardUseCase saveNewHelpcardUseCase;

    public AddCardViewModel(SaveNewHelpcardUseCase saveNewHelpcardUseCase) {
        this.saveNewHelpcardUseCase = saveNewHelpcardUseCase;
    }

    public void saveNewHelpcard(Helpcard helpcard) {
        saveNewHelpcardUseCase.execute(helpcard);
    }

}
