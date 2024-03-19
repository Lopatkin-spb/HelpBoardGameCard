package space.lopatkin.spb.helpboardgamecard.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase

class SettingsViewModel(
    private val saveKeyboardTypeByUserChoiceUseCase: SaveKeyboardTypeByUserChoiceUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private var jobLoadKeyboardType: Job? = null
    private var jobSaveKeyboardType: Job? = null
    private val _uiState = MutableLiveData(SettingsUiState())
    val uiState: LiveData<SettingsUiState> = _uiState

    fun loadKeyboardType() {
        if (jobLoadKeyboardType != null) return
        jobLoadKeyboardType = viewModelScope.launch(dispatchers.main() + CoroutineName(LOAD_KEYBOARD_TYPE)) {

            getKeyboardTypeUseCase.execute()
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { result ->
                    val newUiState = _uiState.value?.copy(isLoading = false, keyboard = result)
                    _uiState.value = newUiState
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(isLoading = false, keyboard = DEFAULT_TYPE)
                    _uiState.value = newUiState
                }
                .onCompletion { finally -> jobLoadKeyboardType = null }
                .collect()
        }
    }

    fun saveKeyboardType(type: Any?) {
        if (jobSaveKeyboardType != null) return
        jobSaveKeyboardType = viewModelScope.launch(dispatchers.main() + CoroutineName(SAVE_KEYBOARD_TYPE)) {

            saveKeyboardTypeByUserChoiceUseCase.execute(type)
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { result ->
                    val newUiState = _uiState.value?.copy(isLoading = false)
                    _uiState.value = newUiState
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(message = Message.ACTION_ENDED_ERROR)
                    _uiState.value = newUiState
                    loadKeyboardType()
                }
                .onCompletion { finally -> jobSaveKeyboardType = null }
                .collect()
        }
    }

    fun messageShowedToUser() {
        val newUiState = _uiState.value?.copy(message = null)
        _uiState.value = newUiState
    }

    fun keyboardInstalledToScreen() {
        val newUiState = _uiState.value?.copy(keyboard = null)
        _uiState.value = newUiState
    }

    companion object {
        private val DEFAULT_TYPE: KeyboardType = KeyboardType.CUSTOM
        private const val SAVE_KEYBOARD_TYPE: String = "coroutine_save_keyboard_type"
        private const val LOAD_KEYBOARD_TYPE: String = "coroutine_load_keyboard_type"
    }

}