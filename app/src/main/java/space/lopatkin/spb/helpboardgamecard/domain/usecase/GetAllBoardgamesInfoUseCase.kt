package space.lopatkin.spb.helpboardgamecard.domain.usecase

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class GetAllBoardgamesInfoUseCase(private val repository: AppRepository) {

    fun execute(): LiveData<List<BoardgameInfo>> {
        return repository.getAllBoardgamesInfo()
    }

}
