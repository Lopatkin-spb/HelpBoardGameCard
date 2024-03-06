package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
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

class CardEditViewModel(
    private val getBoardgameRawByBoardgameIdUseCase: GetBoardgameRawByBoardgameIdUseCase,
    private val updateBoardgameByBoardgameIdUseCase: UpdateBoardgameByBoardgameIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private val _boardgameId = MutableLiveData<Long>()
    private val _keyboardType = MutableLiveData<KeyboardType>()
    private val _message = MutableLiveData<Message>()
    private val _boardgameRaw = MutableLiveData<BoardgameRaw>()
    val boardgameRaw: LiveData<BoardgameRaw> = _boardgameRaw
    val keyboardType: LiveData<KeyboardType> = _keyboardType
    val message: LiveData<Message> = _message

    fun loadKeyboardType() {
        viewModelScope.launch(dispatchers.main() + CoroutineName(LOAD_KEYBOARD_TYPE)) {
            getKeyboardTypeUseCase.execute()
                .cancellable()
                .onEach { result ->
                    _keyboardType.value = result
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    _keyboardType.value = DEFAULT_TYPE
                }
                .collect()
        }
    }

    fun loadBoardgameRaw(boardgameId: Long?) {
        _boardgameId.value = boardgameId //TODO: setter split
        viewModelScope.launch(dispatchers.main() + CoroutineName(LOAD_BOARDGAME_RAW)) {
            getBoardgameRawByBoardgameIdUseCase.execute(boardgameId)
                .cancellable()
                .onEach { result ->
                    _boardgameRaw.value = result
                }
                .onCompletion {
                    //TODO: stop loading
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    _message.value = Message.ACTION_ENDED_ERROR
                }
                .collect()
        }
    }

    fun update(boardgameRaw: BoardgameRaw?) {
        viewModelScope.launch(dispatchers.main() + CoroutineName(UPDATE_BOARDGAME_RAW)) {
            updateBoardgameByBoardgameIdUseCase.execute(boardgameRaw)
                .cancellable()
                .onEach { action ->
                    _message.value = Message.ACTION_ENDED_SUCCESS
                }
                .catch { exception ->
                    //TODO: logging only exception but not error
                    if (exception is ValidationException) {
                        _message.value = Message.ACTION_STOPPED
                    } else {
                        _message.value = Message.ACTION_ENDED_ERROR
                    }
                }
                .collect()
        }
    }

    companion object {
        private val DEFAULT_TYPE: KeyboardType = KeyboardType.CUSTOM
        private const val UPDATE_BOARDGAME_RAW: String = "coroutine_update_boardgame_raw"
        private const val LOAD_BOARDGAME_RAW: String = "coroutine_load_boardgame_raw"
        private const val LOAD_KEYBOARD_TYPE: String = "coroutine_load_keyboard_type"
    }

}