package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class UpdateBoardgameFavoriteByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(boardgameInfo: BoardgameInfo?): Result<Message> {
        if (boardgameInfo == null) {
            return Result.failure(Exception("NotFoundException (usecase): data (BoardgameInfo) is null"))
        }

        return repository.update(boardgameInfo)
            .map { resultSuccessTo -> Message.FAVORITE_ITEM_ACTION_ENDED_SUCCESS }
    }

}
