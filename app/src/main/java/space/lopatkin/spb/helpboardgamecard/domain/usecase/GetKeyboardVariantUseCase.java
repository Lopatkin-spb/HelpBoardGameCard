package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardVariant;

public class GetKeyboardVariantUseCase {

    private HelpcardRepository repository;

    public GetKeyboardVariantUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public KeyboardVariant execute() {
        KeyboardVariant data = KeyboardVariant.getOrdinalTrueFrom(repository.getKeyboardVariant());
        return data;
    }

}
