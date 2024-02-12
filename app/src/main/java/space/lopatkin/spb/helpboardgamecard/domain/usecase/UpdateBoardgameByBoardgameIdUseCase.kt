package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class UpdateBoardgameByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(boardgameRaw: BoardgameRaw?): Message {
        if (boardgameRaw != null && boardgameRaw.name?.isEmpty() == true) {
            return Message.ACTION_STOPPED
        } else if (boardgameRaw != null && boardgameRaw.name?.isNotEmpty() == true) {
            return repository.updateBoardgameBy(boardgameRaw = boardgameRaw)
        }
        return Message.ACTION_ENDED_ERROR
    }

}
