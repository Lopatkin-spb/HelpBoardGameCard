package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;

public class DeleteHelpcardsByLockUseCase {

    private HelpcardRepository repository;

    public DeleteHelpcardsByLockUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.deleteAllUnlockHelpcards();
    }

}
