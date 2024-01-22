package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;

public class DeleteHelpcardUnlockedByHelpcardIdUseCase {

    private AppRepository repository;

    public DeleteHelpcardUnlockedByHelpcardIdUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Message execute(Helpcard helpcard) {
        if (helpcard == null) {
            return Message.ACTION_ENDED_ERROR;
        }

        if (!helpcard.isLock()) {
            repository.delete(helpcard.getId());
            return Message.DELETE_ITEM_ACTION_ENDED_SUCCESS;
        } else if (helpcard.isLock()) {
            return Message.DELETE_ITEM_ACTION_STOPPED;
        }
        return Message.ACTION_ENDED_ERROR;
    }

}
