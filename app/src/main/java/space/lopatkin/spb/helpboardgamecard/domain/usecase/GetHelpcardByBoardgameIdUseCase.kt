package space.lopatkin.spb.helpboardgamecard.domain.usecase

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetHelpcardByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardGameId: Long): LiveData<Helpcard> {
        return repository.getHelpcardBy(boardGameId = boardGameId)
    }

}
