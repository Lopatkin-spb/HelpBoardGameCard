package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.DataPassError
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class DeleteBoardgamesByUnlockStateUseCase(private val repository: BoardgameRepository) {

    suspend fun execute(): Result<Message> {
        val result = try {
            repository.getBoardgamesIdByUnlock()
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
        when (result.isSuccess) {
            true -> {
                val listIdUnlock: Array<Long> = repository.getBoardgamesIdByUnlock().getOrThrow()
                if (listIdUnlock.size > 0) {
                    return repository.deleteUnlockBoardgames()
                } else {
                    return Result.success(Message.DELETE_ALL_ACTION_STOPPED)
                }
            }

            else -> {
                return Result.failure(DataPassError("Data pass is null", IllegalStateException()))
            }
        }
    }

}
