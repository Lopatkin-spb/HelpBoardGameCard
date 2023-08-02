package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class UpdateHelpcardByObjectUseCase {

    private HelpcardRepository repository;

    public UpdateHelpcardByObjectUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public void execute(Helpcard helpcard) {
        repository.update(helpcard);
    }

}
