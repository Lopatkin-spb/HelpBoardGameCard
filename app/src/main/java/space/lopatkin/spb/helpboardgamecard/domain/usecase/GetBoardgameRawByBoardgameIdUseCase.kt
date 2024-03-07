package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetBoardgameRawByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameId: Long?): Flow<BoardgameRaw> {
        return flow {
            if (boardgameId != null && boardgameId > 0) {
                emit(boardgameId)
            } else {
                throw Exception("NotFoundException (usecase): data (boardgameId) is null")
            }
        }.flatMapMerge { id -> repository.getBoardgameRawBy(id) }
    }

}
