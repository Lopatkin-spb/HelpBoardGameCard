package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetHelpcardByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(boardgameId: Long?): Result<Helpcard> {
        if (boardgameId != null && boardgameId > 0) {
            return repository.getHelpcardBy(boardgameId)
        }
        return Result.failure(Exception("NotFoundException (usecase): data (boardgameId) is null"))
    }

}
