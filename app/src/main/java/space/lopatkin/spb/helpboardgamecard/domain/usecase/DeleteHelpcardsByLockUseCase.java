package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;

public class DeleteHelpcardsByLockUseCase {

    private AppRepository repository;

    public DeleteHelpcardsByLockUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.deleteAllUnlockHelpcards();
    }

}
