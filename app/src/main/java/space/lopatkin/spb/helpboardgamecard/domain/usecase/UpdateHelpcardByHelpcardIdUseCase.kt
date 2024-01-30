package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

public class UpdateHelpcardByHelpcardIdUseCase {

    private AppRepository repository;

    public UpdateHelpcardByHelpcardIdUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Message execute(Helpcard helpcard) {
        if (helpcard != null && helpcard.getTitle().isEmpty()) {
            return Message.ACTION_STOPPED;
        } else if (helpcard != null && !helpcard.getTitle().isEmpty()) {
            repository.update(helpcard);
            return Message.ACTION_ENDED_SUCCESS;
        }
        return Message.ACTION_ENDED_ERROR;
    }

}
