package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetAllBoardgamesInfoUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(): Result<List<BoardgameInfo>> {
        return repository.getAllBoardgamesInfo()
    }

}
