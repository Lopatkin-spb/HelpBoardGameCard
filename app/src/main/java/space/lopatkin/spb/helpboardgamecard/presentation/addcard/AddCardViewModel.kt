package space.lopatkin.spb.helpboardgamecard.presentation.addcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveBoardgameNewByBoardgameIdUseCase
import space.lopatkin.spb.helpboardgamecard.presentation.ValidationException

class AddCardViewModel(
    private val saveBoardgameNewByBoardgameIdUseCase: SaveBoardgameNewByBoardgameIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private val _keyboardType = MutableLiveData<KeyboardType>()
    private val _message = MutableLiveData<Message>()
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

    fun saveNewBoardgame(boardgameRaw: BoardgameRaw?) {
        viewModelScope.launch(dispatchers.main() + CoroutineName(SAVE_NEW_BOARDGAME)) {
            saveBoardgameNewByBoardgameIdUseCase.execute(boardgameRaw)
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
        private const val SAVE_NEW_BOARDGAME: String = "coroutine_save_new_boardgame"
        private const val LOAD_KEYBOARD_TYPE: String = "coroutine_load_keyboard_type"
    }

}
