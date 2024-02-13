package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.DataPassError
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class UpdateBoardgameLockingByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(boardgameInfo: BoardgameInfo?): Result<Message> {
        if (boardgameInfo == null) {
            return Result.failure(DataPassError("Data pass is null", IllegalStateException()))
        }

        val result = repository.update(boardgameInfo)
        if (result.getOrNull() == Message.ACTION_ENDED_SUCCESS) {
            return Result.success(Message.LOCKING_ITEM_ACTION_ENDED_SUCCESS)
        }

        return Result.failure(DataPassError("Data pass is null", IllegalStateException()))
    }

}
