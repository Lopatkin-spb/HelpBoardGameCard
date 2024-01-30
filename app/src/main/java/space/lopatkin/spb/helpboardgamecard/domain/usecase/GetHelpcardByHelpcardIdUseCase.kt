package space.lopatkin.spb.helpboardgamecard.domain.usecase

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class GetHelpcardByHelpcardIdUseCase(private val repository: AppRepository) {

    fun execute(boardGameId: Int): LiveData<Helpcard> {
        return repository.getHelpcard(boardGameId)
    }

}
