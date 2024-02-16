package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private val _message = MutableLiveData<Message>()
    private val _listBoardgamesInfo = MutableLiveData<List<BoardgameInfo>>()
    val message: LiveData<Message> = _message
    val listBoardgamesInfo: LiveData<List<BoardgameInfo>> = _listBoardgamesInfo

    fun loadListBoardgamesInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Result<List<BoardgameInfo>> =
                try {
                    getAllBoardgamesInfoUseCase.execute()
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.isSuccess) {
                true -> {
                    _listBoardgamesInfo.postValue(result.getOrNull())
                }

                else -> {
                    //TODO: logging error
                    _message.postValue(Message.ACTION_ENDED_ERROR)
                }
            }
        }
    }

    fun deleteAllUnlockBoardgames() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Result<Message> =
                try {
                    deleteBoardgamesByUnlockStateUseCase.execute()
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.getOrNull()) {
                Message.DELETE_ALL_ACTION_ENDED_SUCCESS -> {
                    _message.postValue(result.getOrNull())
                }

                Message.DELETE_ALL_ACTION_STOPPED -> {
                    _message.postValue(result.getOrNull())
                }

                else -> {
                    //TODO: logging error
                    _message.postValue(Message.DELETE_ALL_ACTION_ENDED_ERROR)
                }
            }
        }
    }

    fun updateFavorite(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Result<Message> =
                try {
                    updateBoardgameFavoriteByBoardgameIdUseCase.execute(boardgameInfo)
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.getOrNull()) {
                Message.FAVORITE_ITEM_ACTION_ENDED_SUCCESS -> {
                    _message.postValue(result.getOrNull())
                }

                else -> {
                    //TODO: logging error
                    _message.postValue(Message.ACTION_ENDED_ERROR)
                    loadListBoardgamesInfo() // Временный костыль - переделать в будущем
                }
            }
        }
    }

    fun updateLocking(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Result<Message> =
                try {
                    updateBoardgameLockingByBoardgameIdUseCase.execute(boardgameInfo)
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.getOrNull()) {
                Message.LOCKING_ITEM_ACTION_ENDED_SUCCESS -> {
                    _message.postValue(result.getOrNull())
                }

                else -> {
                    //TODO: logging error
                    _message.postValue(Message.ACTION_ENDED_ERROR)
                    loadListBoardgamesInfo() // Временный костыль - переделать в будущем
                }
            }
        }
    }

    fun delete(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Result<Message> =
                try {
                    deleteBoardgameUnlockedByBoardgameIdUseCase.execute(boardgameInfo)
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.getOrNull()) {
                Message.DELETE_ITEM_ACTION_ENDED_SUCCESS -> {
                    _message.postValue(result.getOrNull())
                }

                Message.DELETE_ITEM_ACTION_STOPPED -> {
                    _message.postValue(result.getOrNull())
                }

                else -> {
                    //TODO: logging error
                    _message.postValue(Message.ACTION_ENDED_ERROR)
                }
            }
            loadListBoardgamesInfo() // Временный костыль - переделать в будущем
        }
    }

}
