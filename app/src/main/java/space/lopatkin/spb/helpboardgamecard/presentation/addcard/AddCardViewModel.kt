package space.lopatkin.spb.helpboardgamecard.presentation.addcard

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
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveBoardgameNewByBoardgameIdUseCase
import space.lopatkin.spb.helpboardgamecard.presentation.UserSettingsUiState
import space.lopatkin.spb.helpboardgamecard.presentation.ValidationException

class AddCardViewModel(
    private val saveBoardgameNewByBoardgameIdUseCase: SaveBoardgameNewByBoardgameIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private var jobLoadKeyboardType: Job? = null
    private var jobSaveNewBoardgame: Job? = null
    private val _settings = MutableLiveData(UserSettingsUiState())
    private val _uiState = MutableLiveData(AddcardUiState())
    val settings: LiveData<UserSettingsUiState> = _settings
    val uiState: LiveData<AddcardUiState> = _uiState

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

    fun saveNewBoardgame(boardgameRaw: BoardgameRaw?) {
        if (jobSaveNewBoardgame != null) return
        jobSaveNewBoardgame = viewModelScope.launch(dispatchers.main() + CoroutineName(SAVE_NEW_BOARDGAME)) {

            saveBoardgameNewByBoardgameIdUseCase.execute(boardgameRaw)
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true, newBoardgame = boardgameRaw)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { action ->
                    val newUiState =
                        _uiState.value?.copy(
                            isLoading = false,
                            isSaveCompleted = true,
                            message = Message.ACTION_ENDED_SUCCESS,
                            newBoardgame = null
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
                .onCompletion { finally -> jobSaveNewBoardgame = null }
                .collect()
        }
    }

    fun messageShownToUser() {
        val newUiState = _uiState.value?.copy(message = null)
        _uiState.value = newUiState
    }

    companion object {
        private val DEFAULT_TYPE: KeyboardType = KeyboardType.CUSTOM
        private const val SAVE_NEW_BOARDGAME: String = "coroutine_save_new_boardgame"
        private const val LOAD_KEYBOARD_TYPE: String = "coroutine_load_keyboard_type"
    }

}
