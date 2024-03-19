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

    private var jobLoadList: Job? = null
    private var jobDeleteAll: Job? = null
    private var jobUpdateFavorite: Job? = null
    private var jobUpdateLocking: Job? = null
    private var jobDeleteBoardgame: Job? = null
    private val _uiState = MutableLiveData(CatalogUiState())
    val uiState: LiveData<CatalogUiState> = _uiState

    // Longtime job must be cancelling (auto cancelling in vm.onCleared) and log it
    fun loadListBoardgamesInfo() {
        // Protect from concurrent callers (if in progress ,dont trigger it again).
        if (jobLoadList != null) return
        jobLoadList = viewModelScope.launch(dispatchers.main() + CoroutineName(LOAD_BOARDGAMES_INFO)) {

            getAllBoardgamesInfoUseCase.execute()
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { newList ->
                    val newUiState = _uiState.value?.copy(isLoading = false, list = newList)
                    _uiState.value = newUiState
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(
                        isLoading = false, message = Message.ACTION_ENDED_ERROR, list = emptyList()
                    )
                    _uiState.value = newUiState
                }
                .onCompletion { finally -> jobLoadList = null }
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
        if (jobDeleteAll != null) return
        jobDeleteAll = viewModelScope.launch(dispatchers.main() + CoroutineName(DELETE_ALL_UNLOCKS)) {

            deleteBoardgamesByUnlockStateUseCase.execute()
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { success -> loadListBoardgamesInfo() }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(isLoading = false, message = Message.ACTION_ENDED_ERROR)
                    _uiState.value = newUiState
                }
                .onCompletion { finally -> jobDeleteAll = null }
                .collect()
        }
    }

    fun updateFavorite(boardgameInfo: BoardgameInfo?) {
        if (jobUpdateFavorite != null) return
        jobUpdateFavorite = viewModelScope.launch(dispatchers.main() + CoroutineName(UPDATE_FAVORITE)) {

            updateBoardgameFavoriteByBoardgameIdUseCase.execute(boardgameInfo)
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { success ->
                    val newUiState = _uiState.value?.copy(isLoading = false)
                    _uiState.value = newUiState
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(message = Message.ACTION_ENDED_ERROR)
                    _uiState.value = newUiState
                    loadListBoardgamesInfo() // RefreshList Временный maybe костыль - переделать в будущем
                }
                .onCompletion { finally -> jobUpdateFavorite = null }
                .collect()
        }
    }

    fun updateLocking(boardgameInfo: BoardgameInfo?) {
        if (jobUpdateLocking != null) return
        jobUpdateLocking = viewModelScope.launch(dispatchers.main() + CoroutineName(UPDATE_LOCKING)) {

            updateBoardgameLockingByBoardgameIdUseCase.execute(boardgameInfo)
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { success ->
                    val newUiState = _uiState.value?.copy(isLoading = false)
                    _uiState.value = newUiState
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(message = Message.ACTION_ENDED_ERROR)
                    _uiState.value = newUiState
                    loadListBoardgamesInfo() // RefreshList Временный maybe костыль - переделать в будущем
                }
                .onCompletion { finally -> jobUpdateLocking = null }
                .collect()
        }
    }

    fun delete(boardgameInfo: BoardgameInfo?) {
        if (jobDeleteBoardgame != null) return
        jobDeleteBoardgame = viewModelScope.launch(dispatchers.main() + CoroutineName(DELETE_ITEM)) {

            deleteBoardgameUnlockedByBoardgameIdUseCase.execute(boardgameInfo)
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { success -> loadListBoardgamesInfo() }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(message = Message.ACTION_ENDED_ERROR)
                    _uiState.value = newUiState
                    loadListBoardgamesInfo() // RefreshList Временный maybe костыль - переделать в будущем
                }
                .onCompletion { finally -> jobDeleteBoardgame = null }
                .collect()
        }
    }

    fun messageShownToUser() {
        val newUiState = _uiState.value?.copy(message = null)
        _uiState.value = newUiState
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
