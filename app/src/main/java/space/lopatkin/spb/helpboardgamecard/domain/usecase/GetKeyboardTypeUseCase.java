package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;

public class GetKeyboardTypeUseCase {

    private AppRepository repository;

    public GetKeyboardTypeUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public KeyboardType execute() {
        return KeyboardType.getOrdinalFrom(repository.getKeyboardType());
    }

}
