package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class UpdateHelpcardFavoriteByHelpcardIdUseCase(private val repository: AppRepository) {

    fun execute(helpcard: Helpcard?): Message {
        if (helpcard == null) {
            return Message.ACTION_ENDED_ERROR
        }

        repository.update(helpcard)
        return Message.FAVORITE_ITEM_ACTION_ENDED_SUCCESS
    }

}
