package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository
import space.lopatkin.spb.helpboardgamecard.presentation.ValidationException

class SaveBoardgameNewByBoardgameIdUseCase(private val repository: BoardgameRepository) {

    fun execute(boardgameRaw: BoardgameRaw?): Flow<Message> {
        return flow {
            if (boardgameRaw == null) {
                throw Exception("NotFoundException (usecase): data (BoardgameRaw) is null")
            } else if (boardgameRaw.name != null && boardgameRaw.name.isEmpty()) {
                throw ValidationException("Field Name in ${BoardgameRaw::class.java} is empty")
            } else {
                emit(boardgameRaw)
            }
        }.flatMapMerge { data -> repository.saveNewBoardgameBy(data) }
    }

}
