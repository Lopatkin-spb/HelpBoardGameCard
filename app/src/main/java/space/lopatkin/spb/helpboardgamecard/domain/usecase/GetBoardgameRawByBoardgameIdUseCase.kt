package space.lopatkin.spb.helpboardgamecard.domain.usecase

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetBoardgameRawByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardGameId: Long): LiveData<BoardgameRaw> {
        return repository.getBoardgameRawBy(boardgameId = boardGameId)
    }

}
