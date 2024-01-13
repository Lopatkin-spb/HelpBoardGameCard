package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.ui.utils.keyboard.KeyboardVariant;

public class SaveKeyboardVariantUseCase {

    private HelpcardRepository repository;

    public SaveKeyboardVariantUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public void execute(KeyboardVariant keyboardVariant) {
        repository.saveKeyboardVariant(keyboardVariant.ordinal());
    }

}
