package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class DeleteBoardgameUnlockedByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameInfo: BoardgameInfo?): Message {
        if (boardgameInfo == null) {
            return Message.ACTION_ENDED_ERROR
        }
        if (!boardgameInfo.boardgameLock && boardgameInfo.boardgameId != null) {
            repository.deleteBoardgameBy(boardgameId = boardgameInfo.boardgameId)
            return Message.DELETE_ITEM_ACTION_ENDED_SUCCESS
        } else if (boardgameInfo.boardgameLock) {
            return Message.DELETE_ITEM_ACTION_STOPPED
        }
        return Message.ACTION_ENDED_ERROR
    }

}
