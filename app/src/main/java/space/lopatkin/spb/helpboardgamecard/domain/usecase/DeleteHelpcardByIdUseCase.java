package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class DeleteHelpcardByIdUseCase {

    private HelpcardRepository repository;

    public DeleteHelpcardByIdUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public void execute(int id) {
        repository.delete(id);
    }

}
