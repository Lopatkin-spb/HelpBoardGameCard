package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.*
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
    var boardgameRaw: LiveData<BoardgameRaw>? = null
    val keyboardType: LiveData<KeyboardType> = _keyboardType
    val message: LiveData<Message> = _message

    fun loadKeyboardType() {
        _keyboardType.value = getKeyboardTypeUseCase.execute()
    }

    fun loadBoardgameRaw(boardgameId: Long?) {
        if (boardgameId != null && boardgameId > 0) {
            _boardgameId.value = boardgameId
            boardgameRaw = Transformations.switchMap(_boardgameId) { thisId ->
                getBoardgameRawByBoardgameIdUseCase.execute(thisId)
            }
        }
    }

    fun update(boardgameRaw: BoardgameRaw?) {
        val messageResponse: Message = updateBoardgameByBoardgameIdUseCase.execute(boardgameRaw)
        _message.value = messageResponse
        _message.value = Message.POOL_EMPTY
    }

}