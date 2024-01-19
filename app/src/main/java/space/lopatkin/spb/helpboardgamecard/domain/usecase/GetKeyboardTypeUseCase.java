package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;

public class GetKeyboardTypeUseCase {

    private HelpcardRepository repository;

    public GetKeyboardTypeUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public KeyboardType execute() {
        return KeyboardType.getOrdinalFrom(repository.getKeyboardType());
    }

}
