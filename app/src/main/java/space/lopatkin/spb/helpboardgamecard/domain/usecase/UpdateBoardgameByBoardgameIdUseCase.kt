package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository
import space.lopatkin.spb.helpboardgamecard.presentation.ValidationException

class UpdateBoardgameByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameRaw: BoardgameRaw?): Flow<Message> {
        return flow {
            if (boardgameRaw == null) {
                throw Exception("NotFoundException (usecase): data (BoardgameRaw) is null")
            } else if (!boardgameRaw.name.isNullOrEmpty()) {
                emit(boardgameRaw)
            } else {
                throw ValidationException("Field Name in ${BoardgameRaw::class.java} is empty")
            }
        }.flatMapMerge { data -> repository.updateBoardgameBy(data) }
    }

}
