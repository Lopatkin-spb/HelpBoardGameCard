package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetBoardgameRawByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(boardgameId: Long?): Result<BoardgameRaw> {
        if (boardgameId != null && boardgameId > 0) {
            return repository.getBoardgameRawBy(boardgameId)
        }
        return Result.failure(Exception("NotFoundException (usecase): data (boardgameId) is null"))
    }

}
