package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

public class DeleteHelpcardUseCase {

    private AppRepository repository;

    public DeleteHelpcardUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public void execute(Helpcard helpcard) {
        repository.delete(helpcard);
    }

}
