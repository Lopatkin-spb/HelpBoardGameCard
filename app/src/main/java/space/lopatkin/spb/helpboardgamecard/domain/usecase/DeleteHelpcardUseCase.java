package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class DeleteHelpcardUseCase {

    private HelpcardRepository repository;

    public DeleteHelpcardUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public void execute(Helpcard helpcard) {
        repository.delete(helpcard);
    }

}
