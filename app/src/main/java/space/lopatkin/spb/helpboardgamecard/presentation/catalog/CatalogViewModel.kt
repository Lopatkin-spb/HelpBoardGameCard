package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*

class CatalogViewModel(
    private val getAllBoardgamesInfoUseCase: GetAllBoardgamesInfoUseCase,
    private val deleteBoardgameUnlockedByBoardgameIdUseCase: DeleteBoardgameUnlockedByBoardgameIdUseCase,
    private val updateBoardgameFavoriteByBoardgameIdUseCase: UpdateBoardgameFavoriteByBoardgameIdUseCase,
    private val updateBoardgameLockingByBoardgameIdUseCase: UpdateBoardgameLockingByBoardgameIdUseCase,
    private val deleteBoardgamesByUnlockStateUseCase: DeleteBoardgamesByUnlockStateUseCase
) : ViewModel() {

    private val messageMutable = MutableLiveData<Message>()
    val message: LiveData<Message> = messageMutable
    var listBoardgamesInfo: LiveData<List<BoardgameInfo>>? = null

    fun loadListBoardgamesInfo() {
        listBoardgamesInfo = getAllBoardgamesInfoUseCase.execute()
    }

    fun deleteAllUnlockBoardgames() {
        deleteBoardgamesByUnlockStateUseCase.execute()
    }

    fun updateFavorite(boardgameInfo: BoardgameInfo?) {
        val messageResponse: Message = updateBoardgameFavoriteByBoardgameIdUseCase.execute(boardgameInfo)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

    fun updateLocking(boardgameInfo: BoardgameInfo?) {
        val messageResponse: Message = updateBoardgameLockingByBoardgameIdUseCase.execute(boardgameInfo)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

    fun delete(boardgameInfo: BoardgameInfo?) {
        val messageResponse: Message = deleteBoardgameUnlockedByBoardgameIdUseCase.execute(boardgameInfo)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

}
