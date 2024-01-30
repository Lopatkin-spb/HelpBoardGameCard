package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;

public class DeleteHelpcardsByUnlockStateUseCase {

    private AppRepository repository;

    public DeleteHelpcardsByUnlockStateUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.deleteAllUnlockHelpcards();
    }

}
