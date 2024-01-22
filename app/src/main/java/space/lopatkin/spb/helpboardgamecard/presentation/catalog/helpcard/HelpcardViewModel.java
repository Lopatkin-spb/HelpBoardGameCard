package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

public class HelpcardViewModel extends ViewModel {

    private GetHelpcardByHelpcardIdUseCase getHelpcardByHelpcardIdUseCase;
    private MutableLiveData<Integer> helpcardIdMutable = new MutableLiveData<>();
    LiveData<Helpcard> helpcard;
    LiveData<Integer> helpcardId = helpcardIdMutable;

    public HelpcardViewModel(GetHelpcardByHelpcardIdUseCase getHelpcardByHelpcardIdUseCase) {
        this.getHelpcardByHelpcardIdUseCase = getHelpcardByHelpcardIdUseCase;
    }

    public void loadHelpcard(int helpcardId) {
        helpcardIdMutable.setValue(helpcardId);
        helpcard = Transformations.switchMap(helpcardIdMutable, (thisId) -> getHelpcardByHelpcardIdUseCase.execute(thisId));
    }

}
