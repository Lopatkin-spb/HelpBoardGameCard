package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class SaveBoardgameNewByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(boardgameRaw: BoardgameRaw?): Result<Message> {
        if (boardgameRaw == null) {
            return Result.failure(Exception("NotFoundException (usecase): data (BoardgameRaw) is null"))
        }

        if (boardgameRaw.name != null && boardgameRaw.name.isEmpty()) {
            return Result.success(Message.ACTION_STOPPED) // Rework to error maybe
        } else if (!boardgameRaw.name.isNullOrEmpty()) {
            return repository.saveNewBoardgameBy(boardgameRaw)
        }
        return Result.failure(Exception("NotFoundException (usecase): data (BoardgameRaw) is null"))
    }

}
