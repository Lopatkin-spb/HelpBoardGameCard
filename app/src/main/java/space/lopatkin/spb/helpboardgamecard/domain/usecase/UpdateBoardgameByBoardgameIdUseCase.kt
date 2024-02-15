package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class UpdateBoardgameByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(boardgameRaw: BoardgameRaw?): Result<Message> {
        if (boardgameRaw != null && boardgameRaw.name?.isEmpty() == true) {
            return Result.success(Message.ACTION_STOPPED) // Rework to error maybe
        } else if (boardgameRaw != null && boardgameRaw.name?.isNotEmpty() == true) {
            return repository.updateBoardgameBy(boardgameRaw)
        }
        return Result.failure(Exception("NotFoundException (usecase): data (BoardgameRaw) is null"))
    }

}
