package space.lopatkin.spb.helpboardgamecard.domain.usecase

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class GetAllBoardgamesInfoUseCase(private val repository: BoardgameRepository) {

    fun execute(): LiveData<List<BoardgameInfo>> {
        return repository.getAllBoardgamesInfo()
    }

}
