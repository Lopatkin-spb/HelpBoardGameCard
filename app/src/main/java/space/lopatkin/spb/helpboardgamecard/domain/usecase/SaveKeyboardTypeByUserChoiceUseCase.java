package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;

public class SaveKeyboardTypeByUserChoiceUseCase {

    private HelpcardRepository repository;

    public SaveKeyboardTypeByUserChoiceUseCase(HelpcardRepository repository) {
        this.repository = repository;
    }

    public Message execute(Object userChoice) {
        if (userChoice != null) {
            KeyboardType type = KeyboardType.getOrdinalFrom(userChoice.toString());
            repository.saveKeyboardType(type.ordinal());
            return Message.ACTION_ENDED_SUCCESS;
        }
        return Message.ACTION_ENDED_ERROR;
    }

}
