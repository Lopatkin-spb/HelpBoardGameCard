package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.DataPassError
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class DeleteBoardgameUnlockedByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(boardgameInfo: BoardgameInfo?): Result<Message> {
        if (boardgameInfo == null) {
            return Result.failure(DataPassError("Data pass is null", IllegalStateException()))
        }
        if (!boardgameInfo.boardgameLock && boardgameInfo.boardgameId != null) {
            return repository.deleteBoardgameBy(boardgameInfo.boardgameId)
        } else if (boardgameInfo.boardgameLock) {
            return Result.success(Message.DELETE_ITEM_ACTION_STOPPED)
        }
        return Result.failure(DataPassError("Data pass is null", IllegalStateException()))
    }

}
