package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Completable
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class UpdateBoardgameFavoriteByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameInfo: BoardgameInfo?): Flow<Completable> {
        return flow {
            if (boardgameInfo == null) {
                throw Exception("NotFoundException (usecase): data (BoardgameInfo) is null")
            } else {
                emit(boardgameInfo)
            }
        }.flatMapMerge { data -> repository.update(data) }
    }

}
