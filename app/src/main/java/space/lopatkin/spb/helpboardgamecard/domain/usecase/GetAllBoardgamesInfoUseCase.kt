package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetAllBoardgamesInfoUseCase(private val repository: BoardgameRepository) {

    fun execute(): Flow<List<BoardgameInfo>> {
        return repository.getAllBoardgamesInfo()
    }

}
