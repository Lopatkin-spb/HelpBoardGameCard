package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

public class UpdateHelpcardByObjectUseCase {

    private AppRepository repository;

    public UpdateHelpcardByObjectUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public void execute(Helpcard helpcard) {
        repository.update(helpcard);
    }

}
