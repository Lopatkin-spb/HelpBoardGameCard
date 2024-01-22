package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;

public class UpdateHelpcardLockingByHelpcardIdUseCase {

    private AppRepository repository;

    public UpdateHelpcardLockingByHelpcardIdUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Message execute(Helpcard helpcard) {
        if (helpcard == null) {
            return Message.ACTION_ENDED_ERROR;
        }

        repository.update(helpcard);
        return Message.LOCKING_ITEM_ACTION_ENDED_SUCCESS;
    }

}
