package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class UpdateBoardgameLockingByBoardgameIdUseCase(private val repository: AppRepository) {

    fun execute(boardgameInfo: BoardgameInfo?): Message {
        if (boardgameInfo == null) {
            return Message.ACTION_ENDED_ERROR
        }

        repository.update(boardgameInfo = boardgameInfo)
        return Message.LOCKING_ITEM_ACTION_ENDED_SUCCESS
    }

}
