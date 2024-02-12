package space.lopatkin.spb.helpboardgamecard.presentation.addcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveBoardgameNewByBoardgameIdUseCase

class AddCardViewModel(
    private val saveBoardgameNewByBoardgameIdUseCase: SaveBoardgameNewByBoardgameIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase
) : ViewModel() {

    private val _keyboardType = MutableLiveData<KeyboardType>()
    private val _message = MutableLiveData<Message>()
    val keyboardType: LiveData<KeyboardType> = _keyboardType
    val message: LiveData<Message> = _message

    fun loadKeyboardType() {
        viewModelScope.launch {
            try {
                val type: KeyboardType = getKeyboardTypeUseCase.execute()
                _keyboardType.value = type
            } catch (error: Throwable) {
                _keyboardType.value = KeyboardType.CUSTOM
            }
        }
    }

    fun saveNewBoardgame(boardgameRaw: BoardgameRaw?) {
        viewModelScope.launch {
            try {
                val messageResponse: Message = saveBoardgameNewByBoardgameIdUseCase.execute(boardgameRaw!!)
                _message.value = messageResponse
            } catch (error: Throwable) {
                _message.value = Message.ACTION_ENDED_ERROR
            } finally {
                _message.value = Message.POOL_EMPTY
            }
        }
    }

}
