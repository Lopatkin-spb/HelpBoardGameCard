package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

public class SaveNewHelpcardUseCase {

    private AppRepository repository;

    public SaveNewHelpcardUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public void execute(Helpcard helpcard) {
        repository.saveNewHelpcard(helpcard);
    }

}
