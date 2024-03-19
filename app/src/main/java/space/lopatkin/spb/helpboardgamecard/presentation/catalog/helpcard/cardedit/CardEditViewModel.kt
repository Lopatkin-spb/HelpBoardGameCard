package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetBoardgameRawByBoardgameIdUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateBoardgameByBoardgameIdUseCase
import space.lopatkin.spb.helpboardgamecard.presentation.ValidationException
import space.lopatkin.spb.helpboardgamecard.presentation.UserSettingsUiState

class CardEditViewModel(
    private val getBoardgameRawByBoardgameIdUseCase: GetBoardgameRawByBoardgameIdUseCase,
    private val updateBoardgameByBoardgameIdUseCase: UpdateBoardgameByBoardgameIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private var jobLoadKeyboardType: Job? = null
    private var jobLoadBoardgameRaw: Job? = null
    private var jobUpdateBoardgame: Job? = null
    private val _settings = MutableLiveData(UserSettingsUiState())
    val settings: LiveData<UserSettingsUiState> = _settings
    private val _uiState = MutableLiveData(CardEditUiState())
    val uiState: LiveData<CardEditUiState> = _uiState

    fun loadKeyboardType() {
        if (jobLoadKeyboardType != null) return
        jobLoadKeyboardType = viewModelScope.launch(dispatchers.main() + CoroutineName(LOAD_KEYBOARD_TYPE)) {

            getKeyboardTypeUseCase.execute()
                .cancellable()
                .onEach { result -> _settings.value = UserSettingsUiState(keyboard = result) }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    _settings.value = UserSettingsUiState(keyboard = DEFAULT_TYPE)
                }
                .onCompletion { finally -> jobLoadKeyboardType = null }
                .collect()
        }
    }

    fun loadBoardgameRaw(boardgameId: Long?) {
        if (jobLoadBoardgameRaw != null) return
        jobLoadBoardgameRaw = viewModelScope.launch(dispatchers.main() + CoroutineName(LOAD_BOARDGAME_RAW)) {

            getBoardgameRawByBoardgameIdUseCase.execute(boardgameId)
                .onStart {
                    val newUiState =
                        _uiState.value?.copy(isLoading = true, boardgameId = boardgameId) //TODO: setter split
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { result ->
                    val newUiState = _uiState.value?.copy(isLoading = false, boardgameRaw = result)
                    _uiState.value = newUiState
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(
                        isLoading = false, message = Message.ACTION_ENDED_ERROR, boardgameRaw = null
                    )
                    _uiState.value = newUiState
                }
                .onCompletion { finally -> jobLoadBoardgameRaw = null }
                .collect()
        }
    }

    fun update(boardgameRaw: BoardgameRaw?) {
        if (jobUpdateBoardgame != null) return
        jobUpdateBoardgame = viewModelScope.launch(dispatchers.main() + CoroutineName(UPDATE_BOARDGAME_RAW)) {

            updateBoardgameByBoardgameIdUseCase.execute(boardgameRaw)
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true, boardgameRaw = boardgameRaw)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { action ->
                    val newUiState = _uiState.value?.copy(
                        isLoading = false,
                        isUpdateCompleted = true,
                        message = Message.ACTION_ENDED_SUCCESS,
                        boardgameRaw = null
                    )
                    _uiState.value = newUiState
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    if (exception is ValidationException) {
                        val newUiState = _uiState.value?.copy(isLoading = false, message = Message.ACTION_STOPPED)
                        _uiState.value = newUiState
                    } else {
                        val newUiState = _uiState.value?.copy(isLoading = false, message = Message.ACTION_ENDED_ERROR)
                        _uiState.value = newUiState
                    }
                }
                .onCompletion { finally -> jobUpdateBoardgame = null }
                .collect()
        }
    }

    fun messageShownToUser() {
        val newUiState = _uiState.value?.copy(message = null)
        _uiState.value = newUiState
    }

    fun getDataDetailsForUpdate() {
        val newUiState = _uiState.value?.copy(isUpdateStart = true)
        _uiState.value = newUiState
    }

    fun eventPassedToFragment() {
        val newUiState = _uiState.value?.copy(isUpdateStart = false)
        _uiState.value = newUiState
    }

    companion object {
        private val DEFAULT_TYPE: KeyboardType = KeyboardType.CUSTOM
        private const val UPDATE_BOARDGAME_RAW: String = "coroutine_update_boardgame_raw"
        private const val LOAD_BOARDGAME_RAW: String = "coroutine_load_boardgame_raw"
        private const val LOAD_KEYBOARD_TYPE: String = "coroutine_load_keyboard_type"
    }

}