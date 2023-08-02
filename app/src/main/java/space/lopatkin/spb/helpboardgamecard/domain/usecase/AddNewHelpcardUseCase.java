package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class AddNewHelpcardUseCase {

    private HelpcardRepository repository;

    public AddNewHelpcardUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public void execute(Helpcard helpcard) {
        repository.addNewHelpcard(helpcard);
    }

}
