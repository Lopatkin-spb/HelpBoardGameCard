package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByBoardgameIdUseCase

class HelpcardViewModel(
    private val getHelpcardByBoardgameIdUseCase: GetHelpcardByBoardgameIdUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private var jobLoadHelpcard: Job? = null
    private val _uiState = MutableLiveData(HelpcardUiState())
    val uiState: LiveData<HelpcardUiState> = _uiState

    fun loadHelpcard(boardgameId: Long?) {
        if (jobLoadHelpcard != null) return
        jobLoadHelpcard = viewModelScope.launch(dispatchers.main() + CoroutineName(LOAD_HELPCARD)) {

            getHelpcardByBoardgameIdUseCase.execute(boardgameId)
                .onStart {
                    val newUiState = _uiState.value?.copy(isLoading = true, boardgameId = boardgameId)
                    _uiState.value = newUiState
                }
                .cancellable()
                .onEach { result ->
                    val newUiState = _uiState.value?.copy(isLoading = false, helpcard = result)
                    _uiState.value = newUiState
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    val newUiState = _uiState.value?.copy(isLoading = false, message = Message.ACTION_ENDED_ERROR)
                    _uiState.value = newUiState
                }
                .onCompletion { finally -> jobLoadHelpcard = null }
                .collect()
        }
    }

    fun messageShownToUser() {
        val newUiState = _uiState.value?.copy(message = null)
        _uiState.value = newUiState
    }

    fun notifyNavigateToEdit() {
        val boardgameId = _uiState.value?.boardgameId
        if (boardgameId != null) {
            val newUiState = _uiState.value?.copy(isNavigate = true)
            _uiState.value = newUiState
        } else {
            val newUiState = _uiState.value?.copy(message = Message.ACTION_ENDED_ERROR)
            _uiState.value = newUiState
        }
    }

    fun userNavigated() {
        val newUiState = _uiState.value?.copy(isNavigate = false)
        _uiState.value = newUiState
    }

    companion object {
        private const val LOAD_HELPCARD: String = "coroutine_load_helpcard"
    }

}
