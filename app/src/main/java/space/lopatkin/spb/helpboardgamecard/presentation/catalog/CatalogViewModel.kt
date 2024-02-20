package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*

class CatalogViewModel(
    private val getAllBoardgamesInfoUseCase: GetAllBoardgamesInfoUseCase,
    private val deleteBoardgameUnlockedByBoardgameIdUseCase: DeleteBoardgameUnlockedByBoardgameIdUseCase,
    private val updateBoardgameFavoriteByBoardgameIdUseCase: UpdateBoardgameFavoriteByBoardgameIdUseCase,
    private val updateBoardgameLockingByBoardgameIdUseCase: UpdateBoardgameLockingByBoardgameIdUseCase,
    private val deleteBoardgamesByUnlockStateUseCase: DeleteBoardgamesByUnlockStateUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    init {
        loadListBoardgamesInfo()
    }

    private var jobLoadAllBoardgamesInfo: Job? = null
    private var jobDeleteItem: Job? = null
    private val _message = MutableLiveData<Message>()
    private val _listBoardgamesInfo = MutableLiveData<List<BoardgameInfo>>()
    val message: LiveData<Message> = _message
    val listBoardgamesInfo: LiveData<List<BoardgameInfo>> = _listBoardgamesInfo

    fun loadListBoardgamesInfo() {
        jobLoadAllBoardgamesInfo = viewModelScope.launch(dispatchers.io + CoroutineName(LOAD_BOARDGAMES_INFO)) {
            getAllBoardgamesInfoUseCase.execute()
                .onSuccess { listBoardgamesInfo ->
                    _listBoardgamesInfo.postValue(listBoardgamesInfo)
                }
                .onFailure { cause ->
                    //TODO: logging error
                }
        }.also { thisJob ->
            thisJob.invokeOnCompletion { error ->
                if (error != null && error is CancellationException) {
                    // Clear resources
                }
            }
        }
    }


//    fun loadListBoardgamesInfo() {
//        jobLoadAllBoardgamesInfo = viewModelScope.launch(dispatchers.main + CoroutineName(LOAD_BOARDGAMES_INFO)) {
//            Log.d("myLogs", "vm loadListBoardgamesInfo start")
//
//            getAllBoardgamesInfoUseCase.execute()
//                .cancellable()
//
//                .catch { exception ->
//                    Log.e("myLogs", "$exception")
//
//                    Log.d("myLogs", "vm loadListBoardgamesInfo catch error = $exception")
//                    //TODO: logging error
//                }
//                .collect { list ->
//                    Log.d("myLogs", "vm loadListBoardgamesInfo collect list = $list")
//
//                    _listBoardgamesInfo.value = list
//                }
//        }
//    }

    fun deleteAllUnlockBoardgames() {
        viewModelScope.launch(dispatchers.io + CoroutineName(DELETE_ALL_UNLOCKS)) {
            deleteBoardgamesByUnlockStateUseCase.execute()
                .onSuccess { result ->
                    loadListBoardgamesInfo() // RefreshList Временный костыль - переделать в будущем
                }
                .onFailure { cause ->
                    //TODO: logging error
                }
        }
    }

    fun updateFavorite(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch(dispatchers.io + CoroutineName(UPDATE_FAVORITE)) {
            updateBoardgameFavoriteByBoardgameIdUseCase.execute(boardgameInfo)
                .onFailure { cause ->
                    //TODO: logging error
                    loadListBoardgamesInfo() // RefreshList Временный костыль - переделать в будущем
                }
        }
    }

    fun updateLocking(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch(dispatchers.io + CoroutineName(UPDATE_LOCKING)) {
            updateBoardgameLockingByBoardgameIdUseCase.execute(boardgameInfo)
                .onFailure { cause ->
                    //TODO: logging error
                    loadListBoardgamesInfo() // RefreshList Временный костыль - переделать в будущем
                }
        }
    }

    // For example: job cancel (auto cancelling in vm.onCleared)
    fun delete(boardgameInfo: BoardgameInfo?) {
        jobDeleteItem = viewModelScope.launch(dispatchers.io + CoroutineName(DELETE_ITEM)) {
            deleteBoardgameUnlockedByBoardgameIdUseCase.execute(boardgameInfo)
                .onSuccess { result ->
                    loadListBoardgamesInfo() // RefreshList Временный костыль - переделать в будущем
                }
                .onFailure { cause ->
                    //TODO: logging error
                    loadListBoardgamesInfo() // RefreshList Временный костыль - переделать в будущем
                }
        }.also { thisJob ->
            thisJob.invokeOnCompletion { error ->
                if (error != null && error is CancellationException) {
                    // Clear resources
                }
            }
        }
    }

    companion object {
        private const val LOAD_BOARDGAMES_INFO: String = "coroutine_load_list_boardgames_info"
        private const val DELETE_ALL_UNLOCKS: String = "coroutine_delete_all_unlocks"
        private const val UPDATE_FAVORITE: String = "coroutine_update_favorite"
        private const val UPDATE_LOCKING: String = "coroutine_update_locking"
        private const val DELETE_ITEM: String = "coroutine_delete_item"
    }

}
