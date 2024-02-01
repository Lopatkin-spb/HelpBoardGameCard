package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class UpdateHelpcardLockingByHelpcardIdUseCase(private val repository: AppRepository) {

    fun execute(helpcard: Helpcard?): Message {
        if (helpcard == null) {
            return Message.ACTION_ENDED_ERROR
        }

        repository.update(helpcard = helpcard)
        return Message.LOCKING_ITEM_ACTION_ENDED_SUCCESS
    }

}
