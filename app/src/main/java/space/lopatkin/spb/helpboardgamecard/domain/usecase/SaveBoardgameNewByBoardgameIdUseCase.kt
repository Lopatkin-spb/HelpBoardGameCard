package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class SaveBoardgameNewByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameRaw: BoardgameRaw?): Message {
        if (boardgameRaw == null) {
            return Message.ACTION_ENDED_ERROR
        }
        if (boardgameRaw.name != null && boardgameRaw.name.isEmpty()) {
            return Message.ACTION_STOPPED
        } else if (!boardgameRaw.name.isNullOrEmpty()) {
            repository.saveNewBoardgameBy(boardgameRaw = boardgameRaw)
            return Message.ACTION_ENDED_SUCCESS
        }
        return Message.ACTION_ENDED_ERROR
    }

}
