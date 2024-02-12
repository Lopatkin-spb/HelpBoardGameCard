package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetBoardgameRawByBoardgameIdUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateBoardgameByBoardgameIdUseCase

class CardEditViewModel(
    private val getBoardgameRawByBoardgameIdUseCase: GetBoardgameRawByBoardgameIdUseCase,
    private val updateBoardgameByBoardgameIdUseCase: UpdateBoardgameByBoardgameIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase
) : ViewModel() {

    private val _boardgameId = MutableLiveData<Long>()
    private val _keyboardType = MutableLiveData<KeyboardType>()
    private val _message = MutableLiveData<Message>()
    private val _boardgameRaw = MutableLiveData<BoardgameRaw>()
    val boardgameRaw: LiveData<BoardgameRaw> = _boardgameRaw
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

    fun loadBoardgameRaw(boardgameId: Long?) {
        _boardgameId.value = boardgameId
        viewModelScope.launch {
            val result = try {
                getBoardgameRawByBoardgameIdUseCase.execute(boardgameId)
            } catch (cause: Throwable) {
                Result.failure(cause)
            }
            when (result.isSuccess) {
                true -> {
                    _boardgameRaw.value = result.getOrNull()
                }

                else -> {
                    _message.value = Message.ACTION_ENDED_ERROR
                }
            }
        }
    }

    fun update(boardgameRaw: BoardgameRaw?) {
        viewModelScope.launch {
            try {
                val messageResponse: Message = updateBoardgameByBoardgameIdUseCase.execute(boardgameRaw)
                _message.value = messageResponse
            } catch (error: Throwable) {
                _message.value = Message.ACTION_ENDED_ERROR
            } finally {
                _message.value = Message.POOL_EMPTY
            }
        }
    }

}