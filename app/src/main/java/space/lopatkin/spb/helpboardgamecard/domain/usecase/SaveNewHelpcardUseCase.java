package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class SaveNewHelpcardUseCase {

    private HelpcardRepository repository;

    public SaveNewHelpcardUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public void execute(Helpcard helpcard) {
        repository.saveNewHelpcard(helpcard);
    }

}
