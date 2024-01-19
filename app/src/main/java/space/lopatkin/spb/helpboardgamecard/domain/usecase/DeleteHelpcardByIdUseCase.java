package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;

public class DeleteHelpcardByIdUseCase {

    private AppRepository repository;

    public DeleteHelpcardByIdUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public void execute(int id) {
        repository.delete(id);
    }

}
