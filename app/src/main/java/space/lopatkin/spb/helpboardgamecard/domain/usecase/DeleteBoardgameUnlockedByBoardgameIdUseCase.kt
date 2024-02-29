package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Completable
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class DeleteBoardgameUnlockedByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameInfo: BoardgameInfo?): Flow<Completable> {
        return flow {
            if (boardgameInfo == null) {
                throw Exception("ValidationException (usecase): data (BoardgameInfo) is null")
            } else if (boardgameInfo.boardgameId == null) {
                throw Exception("ValidationException (usecase): missing (boardgameId) in (BoardgameInfo)")
            } else if (boardgameInfo.boardgameLock) {
                throw Exception("ValidationException (usecase): data (BoardgameInfo) is locked")
            } else {
                emit(boardgameInfo.boardgameId)
            }
        }.flatMapMerge { id ->
            repository.deleteBoardgameBy(id)
        }
    }

}
