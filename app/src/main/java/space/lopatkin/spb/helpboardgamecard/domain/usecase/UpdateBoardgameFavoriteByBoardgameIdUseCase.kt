package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class UpdateBoardgameFavoriteByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameInfo: BoardgameInfo?): Message {
        if (boardgameInfo == null) {
            return Message.ACTION_ENDED_ERROR
        }

        repository.update(boardgameInfo = boardgameInfo)
        return Message.FAVORITE_ITEM_ACTION_ENDED_SUCCESS
    }

}
