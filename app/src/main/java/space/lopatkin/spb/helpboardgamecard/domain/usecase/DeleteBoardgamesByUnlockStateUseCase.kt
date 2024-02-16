package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class DeleteBoardgamesByUnlockStateUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(): Result<Message> {
        return repository.deleteUnlockBoardgames()
    }

}
