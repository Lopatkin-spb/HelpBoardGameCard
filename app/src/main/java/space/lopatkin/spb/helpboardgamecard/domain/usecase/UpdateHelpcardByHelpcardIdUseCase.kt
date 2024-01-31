package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class UpdateHelpcardByHelpcardIdUseCase(private val repository: AppRepository) {

    fun execute(helpcard: Helpcard?): Message {
        if (helpcard != null && helpcard.title!!.isEmpty()) {
            return Message.ACTION_STOPPED
        } else if (helpcard != null && !helpcard.title!!.isEmpty()) {
            repository.update(helpcard)
            return Message.ACTION_ENDED_SUCCESS
        }
        return Message.ACTION_ENDED_ERROR
    }

}
