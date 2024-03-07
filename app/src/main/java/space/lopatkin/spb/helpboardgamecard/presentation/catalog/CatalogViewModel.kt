package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*
import space.lopatkin.spb.helpboardgamecard.presentation.ForceRefreshCatalogListAfterFirstOpenAppEvent

class CatalogViewModel(
    private val getAllBoardgamesInfoUseCase: GetAllBoardgamesInfoUseCase,
    private val deleteBoardgameUnlockedByBoardgameIdUseCase: DeleteBoardgameUnlockedByBoardgameIdUseCase,
    private val updateBoardgameFavoriteByBoardgameIdUseCase: UpdateBoardgameFavoriteByBoardgameIdUseCase,
    private val updateBoardgameLockingByBoardgameIdUseCase: UpdateBoardgameLockingByBoardgameIdUseCase,
    private val deleteBoardgamesByUnlockStateUseCase: DeleteBoardgamesByUnlockStateUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private var jobLoadAllBoardgamesInfo: Job? = null
    private val _message = MutableLiveData<Message>()
    private val _listBoardgamesInfo = MutableLiveData<List<BoardgameInfo>>()
    val message: LiveData<Message> = _message
    val listBoardgamesInfo: LiveData<List<BoardgameInfo>> = _listBoardgamesInfo

    // Longtime job must be cancelling (auto cancelling in vm.onCleared) and log it
    fun loadListBoardgamesInfo() {
        jobLoadAllBoardgamesInfo = viewModelScope.launch(
            dispatchers.main() + CoroutineName(LOAD_BOARDGAMES_INFO)
        ) {
            getAllBoardgamesInfoUseCase.execute()
                .cancellable()
                .onEach { list ->
                    _listBoardgamesInfo.value = list
                }
                .onCompletion {
                    //TODO: finally block (example - stop loading)
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    _message.value = Message.ACTION_ENDED_ERROR
                }
                .collect()
        }.also { thisJob ->
            thisJob.invokeOnCompletion { error ->
                if (error != null && error is CancellationException) {
                    // Clear resources
                    //TODO: logging only exception but not error
                }
            }
        }
    }

    fun deleteAllUnlockBoardgames() {
        viewModelScope.launch(dispatchers.main() + CoroutineName(DELETE_ALL_UNLOCKS)) {
            deleteBoardgamesByUnlockStateUseCase.execute()
                .cancellable()
                .onEach { success ->
                    loadListBoardgamesInfo() // RefreshList Временный maybe костыль - переделать в будущем
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    _message.value = Message.ACTION_ENDED_ERROR
                }
                .collect()
        }
    }

    fun updateFavorite(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch(dispatchers.main() + CoroutineName(UPDATE_FAVORITE)) {
            updateBoardgameFavoriteByBoardgameIdUseCase.execute(boardgameInfo)
                .cancellable()
                .catch { exception ->
                    //TODO: logging only exception but not error
                    loadListBoardgamesInfo() // RefreshList Временный maybe костыль - переделать в будущем
                    _message.value = Message.ACTION_ENDED_ERROR
                }
                .collect()
        }
    }

    fun updateLocking(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch(dispatchers.main() + CoroutineName(UPDATE_LOCKING)) {
            updateBoardgameLockingByBoardgameIdUseCase.execute(boardgameInfo)
                .cancellable()
                .catch { exception ->
                    //TODO: logging only exception but not error
                    loadListBoardgamesInfo() // RefreshList Временный maybe костыль - переделать в будущем
                    _message.value = Message.ACTION_ENDED_ERROR
                }
                .collect()
        }
    }

    fun delete(boardgameInfo: BoardgameInfo?) {
        viewModelScope.launch(dispatchers.main() + CoroutineName(DELETE_ITEM)) {
            deleteBoardgameUnlockedByBoardgameIdUseCase.execute(boardgameInfo)
                .cancellable()
                .catch { exception ->
                    //TODO: logging only exception but not error
                    loadListBoardgamesInfo() // RefreshList Временный maybe костыль - переделать в будущем
                    _message.value = Message.ACTION_ENDED_ERROR
                }
                .collect()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onForceRefreshCatalogListAfterFirstOpenAppEvent(event: ForceRefreshCatalogListAfterFirstOpenAppEvent) {
        loadListBoardgamesInfo()
    }

    companion object {
        private const val LOAD_BOARDGAMES_INFO: String = "coroutine_load_list_boardgames_info"
        private const val DELETE_ALL_UNLOCKS: String = "coroutine_delete_all_unlocks"
        private const val UPDATE_FAVORITE: String = "coroutine_update_favorite"
        private const val UPDATE_LOCKING: String = "coroutine_update_locking"
        private const val DELETE_ITEM: String = "coroutine_delete_item"
    }

}
