package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class DeleteHelpcardUnlockedByHelpcardIdUseCase(private val repository: AppRepository) {

    fun execute(helpcard: Helpcard?): Message {
        if (helpcard == null) {
            return Message.ACTION_ENDED_ERROR
        }
        if (!helpcard.isLock) {
            repository.delete(helpcard.id)
            return Message.DELETE_ITEM_ACTION_ENDED_SUCCESS
        } else if (helpcard.isLock) {
            return Message.DELETE_ITEM_ACTION_STOPPED
        }
        return Message.ACTION_ENDED_ERROR
    }

}
