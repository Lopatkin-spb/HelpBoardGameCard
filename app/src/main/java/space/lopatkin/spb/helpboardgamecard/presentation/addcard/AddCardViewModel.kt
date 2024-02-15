package space.lopatkin.spb.helpboardgamecard.presentation.addcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.IO) {
            val result: Result<KeyboardType> =
                try {
                    getKeyboardTypeUseCase.execute()
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.isSuccess) {
                true -> {
                    _keyboardType.postValue(result.getOrDefault(DEFAULT_TYPE))
                }

                else -> {
                    //TODO: logging error
                    _keyboardType.postValue(DEFAULT_TYPE)
                }
            }
        }
    }

    fun saveNewBoardgame(boardgameRaw: BoardgameRaw?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Result<Message> =
                try {
                    saveBoardgameNewByBoardgameIdUseCase.execute(boardgameRaw)
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.getOrNull()) {
                Message.ACTION_ENDED_SUCCESS -> {
                    _message.postValue(result.getOrNull())
                }

                Message.ACTION_STOPPED -> {
                    _message.postValue(result.getOrNull())
                }

                else -> {
                    //TODO: logging error
                    _message.postValue(Message.ACTION_ENDED_ERROR)
                }
            }
        }
    }

    companion object {
        private val DEFAULT_TYPE: KeyboardType = KeyboardType.CUSTOM
    }

}
