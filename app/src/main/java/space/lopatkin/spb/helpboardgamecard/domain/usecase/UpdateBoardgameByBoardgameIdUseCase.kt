package space.lopatkin.spb.helpboardgamecard.domain.usecase

import android.util.Log
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class UpdateBoardgameByBoardgameIdUseCase(private val repository: AppRepository) {

    fun execute(boardgameRaw: BoardgameRaw?): Message {
        if (boardgameRaw != null && boardgameRaw.name?.isEmpty() == true) {
            return Message.ACTION_STOPPED
        } else if (boardgameRaw != null && boardgameRaw.name?.isNotEmpty() == true) {
            repository.updateBoardgameBy(boardgameRaw = boardgameRaw)
            return Message.ACTION_ENDED_SUCCESS
        }
        return Message.ACTION_ENDED_ERROR
    }

}
