package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetHelpcardByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameId: Long?): Flow<Helpcard> {
        return flow {
            if (boardgameId != null && boardgameId > 0) {
                emit(boardgameId)
            } else {
                throw Exception("NotFoundException (usecase): data (boardgameId) is null")
            }
        }.flatMapMerge { id -> repository.getHelpcardBy(id) }
    }

}
