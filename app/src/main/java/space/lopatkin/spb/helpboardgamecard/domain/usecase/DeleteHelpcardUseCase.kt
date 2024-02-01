package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class DeleteHelpcardUseCase(private val repository: AppRepository) {

    fun execute(helpcard: Helpcard?) {
        repository.delete(helpcard = helpcard!!)
    }

}
