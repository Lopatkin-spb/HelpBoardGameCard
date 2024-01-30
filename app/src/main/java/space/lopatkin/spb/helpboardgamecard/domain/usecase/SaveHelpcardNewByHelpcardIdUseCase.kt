package space.lopatkin.spb.helpboardgamecard.domain.usecase;

import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

public class SaveHelpcardNewByHelpcardIdUseCase {

    private AppRepository repository;

    public SaveHelpcardNewByHelpcardIdUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Message execute(Helpcard helpcard) {
        if (helpcard == null) {
            return Message.ACTION_ENDED_ERROR;
        }

        if (helpcard.getTitle().isEmpty()) {
            return Message.ACTION_STOPPED;
        } else if (!helpcard.getTitle().isEmpty()) {
            repository.saveNewHelpcard(helpcard);
            return Message.ACTION_ENDED_SUCCESS;
        }
        return Message.ACTION_ENDED_ERROR;
    }

}
