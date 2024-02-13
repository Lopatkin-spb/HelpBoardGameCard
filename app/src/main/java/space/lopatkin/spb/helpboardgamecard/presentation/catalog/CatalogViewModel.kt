package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        viewModelScope.launch {
            val result = try {
                getAllBoardgamesInfoUseCase.execute()
            } catch (cause: Throwable) {
                Result.failure(cause)
            }
            when (result.isSuccess) {
                true -> {
                    _listBoardgamesInfo.value = result.getOrNull()
                }

                else -> {
                    _message.value = Message.ACTION_ENDED_ERROR
                }
            }
        }

    }

    fun deleteAllUnlockBoardgames() {
        viewModelScope.launch {
            val result = try {
                deleteBoardgamesByUnlockStateUseCase.execute()
            } catch (cause: Throwable) {
                Result.failure(cause)
            }
            when (result.getOrNull()) {
                Message.DELETE_ALL_ACTION_ENDED_SUCCESS -> {
                    _message.value = Message.DELETE_ALL_ACTION_ENDED_SUCCESS
                }

                Message.DELETE_ALL_ACTION_STOPPED -> {
                    _message.value = Message.DELETE_ALL_ACTION_STOPPED
                }

                else -> {
                    _message.value = Message.DELETE_ALL_ACTION_ENDED_ERROR
                }
            }
        }

    }

    fun updateFavorite(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch {
            val result = try {
                updateBoardgameFavoriteByBoardgameIdUseCase.execute(boardgameInfo)
            } catch (cause: Throwable) {
                Result.failure(cause)
            }
            when (result.getOrNull()) {
                Message.FAVORITE_ITEM_ACTION_ENDED_SUCCESS -> {
                    _message.value = Message.FAVORITE_ITEM_ACTION_ENDED_SUCCESS
                }

                else -> {
                    _message.value = Message.ACTION_ENDED_ERROR
                }
            }
        }
    }

    fun updateLocking(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch {
            val result = try {
                updateBoardgameLockingByBoardgameIdUseCase.execute(boardgameInfo)
            } catch (cause: Throwable) {
                Result.failure(cause)
            }
            when (result.getOrNull()) {
                Message.LOCKING_ITEM_ACTION_ENDED_SUCCESS -> {
                    _message.value = Message.LOCKING_ITEM_ACTION_ENDED_SUCCESS
                }


                else -> {
                    _message.value = Message.ACTION_ENDED_ERROR
                }
            }
        }
    }

    fun delete(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch {
            val result = try {
                deleteBoardgameUnlockedByBoardgameIdUseCase.execute(boardgameInfo)
            } catch (cause: Throwable) {
                Result.failure(cause)
            }
            when (result.getOrNull()) {
                Message.DELETE_ITEM_ACTION_STOPPED -> {
                    _message.value = Message.DELETE_ITEM_ACTION_STOPPED
                }

                Message.DELETE_ITEM_ACTION_ENDED_SUCCESS -> {
                    _message.value = Message.DELETE_ITEM_ACTION_ENDED_SUCCESS
                }

                else -> {
                    _message.value = Message.ACTION_ENDED_ERROR
                }
            }
        }
    }

}
