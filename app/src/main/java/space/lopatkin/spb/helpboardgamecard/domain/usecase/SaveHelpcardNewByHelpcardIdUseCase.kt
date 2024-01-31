package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class SaveHelpcardNewByHelpcardIdUseCase(private val repository: AppRepository) {

    fun execute(helpcard: Helpcard?): Message {
        if (helpcard == null) {
            return Message.ACTION_ENDED_ERROR
        }
        if (helpcard.title!!.isEmpty()) {
            return Message.ACTION_STOPPED
        } else if (!helpcard.title!!.isEmpty()) {
            repository.saveNewHelpcard(helpcard)
            return Message.ACTION_ENDED_SUCCESS
        }
        return Message.ACTION_ENDED_ERROR
    }

}
