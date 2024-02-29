package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.domain.model.Completable
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class DeleteBoardgamesByUnlockStateUseCase(private val repository: BoardgameRepository) {

    fun execute(): Flow<Completable> {
        return repository.deleteUnlockBoardgames()
    }

}
